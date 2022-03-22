package com.github.pksokolowski.currencyconverter.common

import android.icu.math.BigDecimal
import android.icu.math.MathContext


fun computeExchangeValue(
    amount: BigDecimal,
    soldCurrencyRate: BigDecimal,
    purchasedCurrencyRate: BigDecimal,
): BigDecimal {
    val baseAmount = amount.divide(soldCurrencyRate)
    val unscaledResult = baseAmount.multiply(purchasedCurrencyRate)
    return unscaledResult.setScale(2, MathContext.ROUND_DOWN)
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