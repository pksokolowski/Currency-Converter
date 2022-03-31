package com.github.pksokolowski.currencyconverter.backend.model

data class UserWallet(
    val currencies: List<CurrencySubWallet>
)