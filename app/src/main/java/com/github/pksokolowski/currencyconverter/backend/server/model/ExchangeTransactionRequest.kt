package com.github.pksokolowski.currencyconverter.backend.server.model

import android.icu.math.BigDecimal

data class ExchangeTransactionRequest(
    val sellCurrencyCode: String,
    val buyCurrencyCode: String,
    val sellAmount: BigDecimal,
    val expectedBuyAmount: BigDecimal
)