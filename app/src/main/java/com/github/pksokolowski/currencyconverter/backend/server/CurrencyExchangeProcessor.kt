package com.github.pksokolowski.currencyconverter.backend.server

import android.icu.math.BigDecimal
import com.github.pksokolowski.currencyconverter.backend.server.model.ExchangeTransactionRequest
import com.github.pksokolowski.currencyconverter.backend.server.model.ExchangeTransactionResult
import com.github.pksokolowski.currencyconverter.backend.server.repository.EuroBasedCurrencyRatesRepository
import com.github.pksokolowski.currencyconverter.backend.server.repository.UserDataRepository
import com.github.pksokolowski.currencyconverter.common.computeExchangeValue
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyExchangeProcessor @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val exchangeRatesRepository: EuroBasedCurrencyRatesRepository,
) {
    suspend fun process(transaction: ExchangeTransactionRequest): ExchangeTransactionResult {
        require(transaction.sellCurrencyCode != transaction.buyCurrencyCode) {
            return error("Attempted to exchange currency to itself")
        }
        require(transaction.sellAmount > BigDecimal("0.00")) {
            return error("Only non-negative and non-zero amounts of currency can be sold")
        }
        require(transaction.expectedBuyAmount > BigDecimal("0.00")) {
            return error("Attempted to buy nothing or a negative amount of currency")
        }

        val currentRates =
            exchangeRatesRepository.getAllRates() ?: return error("Internal server error")

        val sellCurrencyRate = currentRates[transaction.sellCurrencyCode]
            ?: return error("Traded currency's exchange rate not available")
        val buyCurrencyRate = currentRates[transaction.sellCurrencyCode]
            ?: return error("Exchange rate not available for the currency to be purchased")

        val convertedAmount =
            computeExchangeValue(transaction.sellAmount, sellCurrencyRate, buyCurrencyRate)

        require(convertedAmount == transaction.expectedBuyAmount) {
            return error("unexpected exchange result. Rates might have changed.")
        }

        val commissionFee = if (userDataRepository.transactionsCounter > 7) {
            BigDecimal("0.007").multiply(transaction.sellAmount)
        } else {
            BigDecimal("0.00")
        }

        // kind of simulation of transaction integrity enforcement
        withContext(NonCancellable) {
            userDataRepository.changeSubWallet(
                transaction.sellCurrencyCode,
                transaction.sellAmount.add(commissionFee).multiply(BigDecimal("-1.00"))
            )
            userDataRepository.changeSubWallet(
                transaction.buyCurrencyCode,
                convertedAmount
            )
        }

        userDataRepository.transactionsCounter += 1

        return ExchangeTransactionResult(
            accepted = true, """"
            |You've converted ${transaction.sellAmount} ${transaction.sellCurrencyCode} 
            |to ${transaction.expectedBuyAmount} ${transaction.buyCurrencyCode}
            |Commission fee: $commissionFee""".trimMargin()
        )
    }

    private fun error(message: String) = ExchangeTransactionResult(false, message)
}