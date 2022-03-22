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

fun computeExchangeValue(
    amount: String,
    soldCurrencyRate: BigDecimal,
    purchasedCurrencyRate: BigDecimal,
): BigDecimal = computeExchangeValue(
    BigDecimal(amount),
    soldCurrencyRate,
    purchasedCurrencyRate
)