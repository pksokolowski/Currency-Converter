package com.github.pksokolowski.currencyconverter.di

import com.github.pksokolowski.currencyconverter.backend.BackendApi
import com.github.pksokolowski.currencyconverter.backend.BackendApiImpl
import com.github.pksokolowski.currencyconverter.backend.CurrencyExchangeProcessor
import com.github.pksokolowski.currencyconverter.backend.commissions.CommissionCalculator
import com.github.pksokolowski.currencyconverter.backend.repository.EuroBasedCurrencyRatesRepository
import com.github.pksokolowski.currencyconverter.backend.repository.UserDataRepository
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
        currencyExchangeProcessor: CurrencyExchangeProcessor,
        commissionCalculator: CommissionCalculator
    ): BackendApi =
        BackendApiImpl(
            userDataRepository,
            euroBasedCurrencyRatesRepository,
            currencyExchangeProcessor,
            commissionCalculator,
        )
}