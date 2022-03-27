package com.github.pksokolowski.currencyconverter.backend.server.repository

import android.icu.math.BigDecimal
import android.icu.math.MathContext
import com.github.pksokolowski.currencyconverter.backend.server.model.CurrencySubWallet
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class UserDataRepository {
    private val mutex = Mutex()

    var transactionsCounter = 0

    fun getUserData(): List<CurrencySubWallet> = usersSubWallets.sortedBy { it.currencyCode }

    // mock users data, for one test user only
    private val usersSubWallets = mutableListOf(
        CurrencySubWallet("EUR", BigDecimal("1000.00")),
        CurrencySubWallet("JPY", BigDecimal("0.00")),
        CurrencySubWallet("USD", BigDecimal("0.00")),
        CurrencySubWallet("GBP", BigDecimal("0.00")),
    )

    suspend fun changeSubWallet(currencyCode: String, addedAmount: BigDecimal) =
        withContext(NonCancellable) {
            mutex.withLock {
                val subWallet = usersSubWallets.find { it.currencyCode == currencyCode } ?: let {
                    CurrencySubWallet(currencyCode, BigDecimal("0.00"))
                        .also { usersSubWallets.add(it) }
                }

                val roundingPolicy = if (addedAmount > BigDecimal(0)) MathContext.ROUND_DOWN
                else MathContext.ROUND_UP

                val modifiedSubWallet = CurrencySubWallet(
                    currencyCode,
                    subWallet.amount
                        .add(addedAmount)
                        .setScale(2, roundingPolicy)
                )

                if (modifiedSubWallet.amount < BigDecimal(0)) throw InsufficientFundsException()

                usersSubWallets.removeIf { it.currencyCode == currencyCode }
                usersSubWallets.add(modifiedSubWallet)
            }
        }
}

class InsufficientFundsException : Exception()