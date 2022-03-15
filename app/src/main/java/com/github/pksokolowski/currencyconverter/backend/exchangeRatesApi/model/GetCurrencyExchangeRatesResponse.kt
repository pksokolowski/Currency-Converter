package com.github.pksokolowski.currencyconverter.backend.exchangeRatesApi.model

data class GetCurrencyExchangeRatesResponse(
    val success: Boolean,
    val rates: Map<String, String>?
)