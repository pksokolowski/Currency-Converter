package com.github.pksokolowski.currencyconverter.backend.server

import android.icu.math.BigDecimal
import com.github.pksokolowski.currencyconverter.backend.BackendApi
import com.github.pksokolowski.currencyconverter.backend.server.model.ExchangeTransactionRequest
import com.github.pksokolowski.currencyconverter.backend.server.model.ExchangeTransactionResult
import com.github.pksokolowski.currencyconverter.backend.server.model.UserWallet
import com.github.pksokolowski.currencyconverter.backend.server.repository.EuroBasedCurrencyRatesRepository
import com.github.pksokolowski.currencyconverter.backend.server.repository.UserDataRepository
import javax.inject.Inject

class BackendApiImpl @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val exchangeRatesRepository: EuroBasedCurrencyRatesRepository,
    private val currencyExchangeProcessor: CurrencyExchangeProcessor,
) : BackendApi {
    override suspend fun getUserWallet(): UserWallet? {
        return UserWallet(userDataRepository.getUserData())
    }

    override suspend fun getExchangeRates(): Map<String, BigDecimal>? {
        return exchangeRatesRepository.getAllRates()
    }

    override suspend fun performExchange(exchangeTransactionRequest: ExchangeTransactionRequest): ExchangeTransactionResult {
        return currencyExchangeProcessor.process(exchangeTransactionRequest)
    }
}