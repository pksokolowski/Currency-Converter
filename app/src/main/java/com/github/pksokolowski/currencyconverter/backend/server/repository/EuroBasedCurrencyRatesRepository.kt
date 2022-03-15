package com.github.pksokolowski.currencyconverter.backend.server.repository

import com.github.pksokolowski.currencyconverter.backend.exchangeRatesApi.CurrencyExchangeRatesClient
import com.github.pksokolowski.currencyconverter.backend.server.utils.CurrentTimeProvider
import com.github.pksokolowski.currencyconverter.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import javax.inject.Inject

class EuroBasedCurrencyRatesRepository @Inject constructor(
    private val apiClient: CurrencyExchangeRatesClient,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val currentTimeProvider: CurrentTimeProvider
) {
    private val mutex = Mutex()
    private var lastUpdateTimeStamp: Long = 0
    private var lastReturnedValue: Map<String, BigDecimal>? = null

    /**
     * returns all currency exchange rates, with EUR as base.
     * Allows up to a fixed frequency of updates, more frequent
     * requests will result in returning a cached response.
     * @return map of currency code to BigDecimal exchange rate representation.
     */
    suspend fun getAllRates(): Map<String, BigDecimal>? = withContext(dispatcher) {
        mutex.withLock {
            val now = currentTimeProvider.currentTimeMillis()
            if (now - lastUpdateTimeStamp <= REFRESH_MIN_DELAY_MILLIS) {
                return@withContext lastReturnedValue
            }

            val response = apiClient.getLatest() ?: return@withContext null
            lastReturnedValue = response.rates.mapValues {
                BigDecimal(it.value)
            }
            lastReturnedValue
        }
    }

}

private const val REFRESH_MIN_DELAY_MILLIS = 5_000