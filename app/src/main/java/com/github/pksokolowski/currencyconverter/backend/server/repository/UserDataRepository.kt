package com.github.pksokolowski.currencyconverter.backend.server.repository

import android.icu.math.BigDecimal
import com.github.pksokolowski.currencyconverter.backend.server.model.CurrencySubWallet
import javax.inject.Inject

class UserDataRepository @Inject constructor() {
    fun getUserData() = usersSubWallets

    // mock users data, for one test user only
    private val usersSubWallets = listOf(
        CurrencySubWallet("EUR", BigDecimal("1000.00")),
        CurrencySubWallet("JPY", BigDecimal("0.00")),
        CurrencySubWallet("USD", BigDecimal("0.00")),
        CurrencySubWallet("GBP", BigDecimal("0.00")),
    )
}