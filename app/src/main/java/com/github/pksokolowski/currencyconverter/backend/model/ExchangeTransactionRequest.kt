package com.github.pksokolowski.currencyconverter.backend.model

import android.icu.math.BigDecimal

data class ExchangeTransactionRequest(
    val sellCurrencyCode: String,
    val buyCurrencyCode: String,
    val sellAmount: BigDecimal,
    val expectedBuyAmount: BigDecimal
)