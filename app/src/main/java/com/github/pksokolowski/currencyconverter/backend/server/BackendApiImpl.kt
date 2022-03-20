package com.github.pksokolowski.currencyconverter.backend.server

import com.github.pksokolowski.currencyconverter.backend.BackendApi
import com.github.pksokolowski.currencyconverter.backend.server.model.ExchangeTransactionRequest
import com.github.pksokolowski.currencyconverter.backend.server.model.ExchangeTransactionResult
import com.github.pksokolowski.currencyconverter.backend.server.model.UserWallet
import com.github.pksokolowski.currencyconverter.backend.server.repository.EuroBasedCurrencyRatesRepository
import com.github.pksokolowski.currencyconverter.backend.server.repository.UserDataRepository
import java.math.BigDecimal
import javax.inject.Inject

class BackendApiImpl @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val exchangeRatesRepository: EuroBasedCurrencyRatesRepository
) : BackendApi {
    override suspend fun getUserWallet(): UserWallet? {
        return UserWallet(userDataRepository.getUserData())
    }

    override suspend fun getExchangeRates(): Map<String, BigDecimal>? {
        return exchangeRatesRepository.getAllRates()
    }

    override suspend fun performExchange(exchangeTransactionRequest: ExchangeTransactionRequest): ExchangeTransactionResult {
        return ExchangeTransactionResult(true, "Pykło wybornie milordzie!")
    }
}