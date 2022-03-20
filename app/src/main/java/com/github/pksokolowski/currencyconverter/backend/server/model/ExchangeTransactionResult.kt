package com.github.pksokolowski.currencyconverter.backend.server.model

data class ExchangeTransactionResult(
    val accepted: Boolean,
    val message: String
)