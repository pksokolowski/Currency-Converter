package com.github.pksokolowski.currencyconverter.domain

import com.github.pksokolowski.currencyconverter.backend.BackendApi
import com.github.pksokolowski.currencyconverter.backend.model.CurrencySubWallet
import com.github.pksokolowski.currencyconverter.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ObtainSubWalletsUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val backendApi: BackendApi
) {
    suspend fun fetchSubWallets(): List<CurrencySubWallet>? = withContext(ioDispatcher) {
        backendApi.getUserWallet()?.currencies
    }
}