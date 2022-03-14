package com.github.pksokolowski.currencyconverter.backend.exchangeRatesApi.model

import com.google.gson.Gson
import org.junit.Assert.assertTrue
import org.junit.Test

class GetCurrencyExchangeRatesResponseParsingTest {
    @Test
    fun modelOfTheResponseWorksForUSD() {
        val responseJson = ClassLoader
            .getSystemResource("responses/currencyRatesApiResponse.json")
            .readText()

        val gson = Gson()
        val parsed = gson.fromJson(responseJson, GetCurrencyExchangeRatesResponse::class.java)

        assertTrue(parsed.rates["USD"] == "1.094721")
    }
}