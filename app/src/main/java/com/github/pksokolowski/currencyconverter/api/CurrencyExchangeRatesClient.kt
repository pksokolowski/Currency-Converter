package com.github.pksokolowski.currencyconverter.api

import com.github.pksokolowski.currencyconverter.api.model.GetCurrencyExchangeRatesResponse
import retrofit2.http.GET

interface CurrencyExchangeRatesClient {
    @GET("latest")
    suspend fun getLatest(): GetCurrencyExchangeRatesResponse?
}