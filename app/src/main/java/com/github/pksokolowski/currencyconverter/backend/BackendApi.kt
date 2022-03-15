package com.github.pksokolowski.currencyconverter.backend

import com.github.pksokolowski.currencyconverter.backend.server.model.UserWallet
import java.math.BigDecimal

interface BackendApi {
    suspend fun getUserWallet(): UserWallet?
    suspend fun getExchangeRateFor(currencyCode: String): BigDecimal?
}