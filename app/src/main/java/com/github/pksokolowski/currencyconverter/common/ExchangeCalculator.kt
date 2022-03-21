package com.github.pksokolowski.currencyconverter.common

import android.icu.math.BigDecimal

fun computeExchangeValue(
    amount: BigDecimal,
    soldCurrencyRate: BigDecimal,
    purchasedCurrencyRate: BigDecimal,
): BigDecimal {
    val baseAmount = amount.divide(soldCurrencyRate)
    return baseAmount.multiply(purchasedCurrencyRate)
}