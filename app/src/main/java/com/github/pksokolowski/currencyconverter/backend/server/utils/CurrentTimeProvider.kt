package com.github.pksokolowski.currencyconverter.backend.server.utils

import javax.inject.Inject

class CurrentTimeProvider @Inject constructor() {
    fun currentTimeMillis() = System.currentTimeMillis()
}