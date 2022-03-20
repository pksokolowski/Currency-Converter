package com.github.pksokolowski.currencyconverter.domain

import android.icu.math.BigDecimal
import com.github.pksokolowski.currencyconverter.backend.BackendApi
import com.github.pksokolowski.currencyconverter.backend.server.model.ExchangeTransactionRequest
import com.github.pksokolowski.currencyconverter.backend.server.model.ExchangeTransactionResult
import javax.inject.Inject

class PerformExchangeUseCase @Inject constructor(
    private val backendApi: BackendApi
) {
    suspend fun exchange(
        sellAmount: String,
        sellCurrencyCode: String,
        buyAmount: String,
        buyCurrencyCode: String
    ): ExchangeTransactionResult {
        val exchangeTransactionRequest = ExchangeTransactionRequest(
            sellCurrencyCode = sellCurrencyCode,
            buyCurrencyCode = buyCurrencyCode,
            sellAmount = BigDecimal(sellAmount),
            expectedBuyAmount = BigDecimal(buyAmount)
        )
        return backendApi.performExchange(exchangeTransactionRequest)
    }
}