package com.github.pksokolowski.currencyconverter.backend.commissions

import android.icu.math.BigDecimal
import android.icu.math.MathContext
import com.github.pksokolowski.currencyconverter.backend.repository.UserDataRepository

/**
 * Decides if and what commission should be applied for a transaction.
 * Like the rest of the backend mock, it assumes existence of just a
 * single user, for simplicity' sake.
 *
 * Another option would be to have some sort of a provider of
 * CommissionCalculator implementations, which would differentiate
 * by something, say a plan a given user purchased, with the free
 * plan being the most expensive in the long run, while payed ones
 * would have some extras and be in general more suitable for
 * heavier, more frequent use.
 */
class FreeBeginningsCommissionCalculator(
    private val userDataRepository: UserDataRepository
) : CommissionCalculator {
    override fun computeCommission(
        sourceCurrencyCode: String,
        purchasedCurrencyCode: String,
        sellAmount: BigDecimal,
        purchaseAmount: BigDecimal
    ): BigDecimal {
        val transactionsCount = userDataRepository.transactionsCounter

        val commissionFee = if (transactionsCount >= FREE_TRANSACTIONS_LIMIT_INCLUSIVE) {
            BigDecimal("0.007")
                .multiply(sellAmount)
                .setScale(2, MathContext.ROUND_UP)
        } else {
            BigDecimal("0.00")
        }

        return commissionFee
    }

    override fun getCurrentStatusMessage(): String {
        val freeTransactionsLeft =
            FREE_TRANSACTIONS_LIMIT_INCLUSIVE - userDataRepository.transactionsCounter

        return if (freeTransactionsLeft > 0) {
            "You have $freeTransactionsLeft free transactions left!"
        } else {
            "0.7% commission applies."
        }
    }
}

const val FREE_TRANSACTIONS_LIMIT_INCLUSIVE = 7