package com.github.pksokolowski.currencyconverter.domain

import com.github.pksokolowski.currencyconverter.backend.BackendApi
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import javax.inject.Inject

class ObtainExchangeRateUseCase @Inject constructor(
    private val backendApi: BackendApi
) {
    fun getUpdates() = flow {
        while (currentCoroutineContext().isActive) {
            val rates = backendApi.getExchangeRates()
            emit(rates)
            delay(5000)
        }
    }
}