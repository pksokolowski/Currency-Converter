package com.github.pksokolowski.currencyconverter.di

import com.github.pksokolowski.currencyconverter.backend.BackendApi
import com.github.pksokolowski.currencyconverter.backend.server.BackendApiImpl
import com.github.pksokolowski.currencyconverter.backend.server.CurrencyExchangeProcessor
import com.github.pksokolowski.currencyconverter.backend.server.repository.EuroBasedCurrencyRatesRepository
import com.github.pksokolowski.currencyconverter.backend.server.repository.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    fun provideBackendApiMock(
        userDataRepository: UserDataRepository,
        euroBasedCurrencyRatesRepository: EuroBasedCurrencyRatesRepository,
        currencyExchangeProcessor: CurrencyExchangeProcessor
    ): BackendApi =
        BackendApiImpl(
            userDataRepository,
            euroBasedCurrencyRatesRepository,
            currencyExchangeProcessor
        )
}