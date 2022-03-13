package com.github.pksokolowski.currencyconverter.backend

import com.github.pksokolowski.currencyconverter.backend.server.model.UserWallet

interface BackendApi {
    suspend fun getUserWallet(): UserWallet?
    // todo add: getExchangeRatesForSelectedCurrencies, submitExchangeOperation, confirmExchangeOperation
}