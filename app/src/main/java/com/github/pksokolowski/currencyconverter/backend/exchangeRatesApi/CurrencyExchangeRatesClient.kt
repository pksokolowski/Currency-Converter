package com.github.pksokolowski.currencyconverter.backend.exchangeRatesApi

import com.github.pksokolowski.currencyconverter.backend.exchangeRatesApi.model.GetCurrencyExchangeRatesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyExchangeRatesClient {
    @GET("latest")
    suspend fun getLatest(@Query("access_key") name: String): GetCurrencyExchangeRatesResponse?
}