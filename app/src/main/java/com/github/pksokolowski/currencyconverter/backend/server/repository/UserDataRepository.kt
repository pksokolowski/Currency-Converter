package com.github.pksokolowski.currencyconverter.backend.server.repository

import android.icu.math.BigDecimal
import com.github.pksokolowski.currencyconverter.backend.server.model.CurrencySubWallet
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDataRepository @Inject constructor() {
    private val mutex = Mutex()

    var transactionsCounter = 0

    fun getUserData(): List<CurrencySubWallet> = usersSubWallets

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

                val modifiedSubWallet =
                    CurrencySubWallet(currencyCode, subWallet.amount.add(addedAmount))

                usersSubWallets.removeIf { it.currencyCode == currencyCode }
                usersSubWallets.add(modifiedSubWallet)
            }
        }
}