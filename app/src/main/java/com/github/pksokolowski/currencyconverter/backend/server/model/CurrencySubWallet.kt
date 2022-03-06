package com.github.pksokolowski.currencyconverter.backend.server.model

import android.icu.math.BigDecimal

data class CurrencySubWallet(
    val currencyCode: String,
    val amount: BigDecimal,
)