package com.github.pksokolowski.currencyconverter.backend.exchangeRatesApi

import com.github.pksokolowski.currencyconverter.backend.exchangeRatesApi.model.GetCurrencyExchangeRatesResponse
import retrofit2.http.GET

interface CurrencyExchangeRatesClient {
    @GET("latest")
    suspend fun getLatest(): GetCurrencyExchangeRatesResponse?
}