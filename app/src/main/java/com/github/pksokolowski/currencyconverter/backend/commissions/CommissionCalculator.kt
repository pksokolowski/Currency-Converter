package com.github.pksokolowski.currencyconverter.backend.commissions

import android.icu.math.BigDecimal

interface CommissionCalculator {
    fun computeCommission(
        sourceCurrencyCode: String,
        purchasedCurrencyCode: String,
        sellAmount: BigDecimal,
        purchaseAmount: BigDecimal
    ): BigDecimal

    fun getCurrentStatusMessage(): String
}