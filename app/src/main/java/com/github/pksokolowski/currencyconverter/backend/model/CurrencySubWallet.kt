package com.github.pksokolowski.currencyconverter.backend.model

import android.icu.math.BigDecimal

data class CurrencySubWallet(
    val currencyCode: String,
    val amount: BigDecimal,
)