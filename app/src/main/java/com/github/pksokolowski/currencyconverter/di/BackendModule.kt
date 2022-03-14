package com.github.pksokolowski.currencyconverter.di

import com.github.pksokolowski.currencyconverter.backend.exchangeRatesApi.CurrencyExchangeRatesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A part of backend mock
 */
@InstallIn(SingletonComponent::class)
@Module
object BackendModule {
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.exchangeratesapi.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideAirQualityService(retrofit: Retrofit): CurrencyExchangeRatesClient =
        retrofit.create(CurrencyExchangeRatesClient::class.java)

}