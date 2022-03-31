package com.github.pksokolowski.currencyconverter.backend.model

data class ExchangeTransactionResult(
    val accepted: Boolean,
    val message: String
)