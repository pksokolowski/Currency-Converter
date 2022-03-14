package com.github.pksokolowski.currencyconverter.backend.server.repository

import com.github.pksokolowski.currencyconverter.backend.exchangeRatesApi.CurrencyExchangeRatesClient
import com.github.pksokolowski.currencyconverter.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import javax.inject.Inject

class EuroBasedCurrencyRatesRepository @Inject constructor(
    private val apiClient: CurrencyExchangeRatesClient,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun getAllRates(): Map<String, BigDecimal>? = withContext(dispatcher) {
        val response = apiClient.getLatest() ?: return@withContext null
        response.rates.mapValues {
            BigDecimal(it.value)
        }
    }
}