package com.github.pksokolowski.currencyconverter.backend.exchangeRatesApi.model

data class GetCurrencyExchangeRatesResponse(
    val rates: Map<String, String>
)