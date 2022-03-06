package com.github.pksokolowski.currencyconverter.backend.server.model

data class UserWallet(
    val currencies: List<CurrencySubWallet>
)