package com.github.pksokolowski.currencyconverter.backend

import android.icu.math.BigDecimal
import com.github.pksokolowski.currencyconverter.backend.server.model.ExchangeTransactionRequest
import com.github.pksokolowski.currencyconverter.backend.server.model.ExchangeTransactionResult
import com.github.pksokolowski.currencyconverter.backend.server.model.UserWallet

interface BackendApi {
    suspend fun getUserWallet(): UserWallet?
    suspend fun getExchangeRates(): Map<String, BigDecimal>?
    suspend fun performExchange(exchangeTransactionRequest: ExchangeTransactionRequest): ExchangeTransactionResult
}