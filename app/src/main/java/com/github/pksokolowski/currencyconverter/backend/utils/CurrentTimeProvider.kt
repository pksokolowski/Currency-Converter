package com.github.pksokolowski.currencyconverter.backend.utils

import javax.inject.Inject

class CurrentTimeProvider @Inject constructor() {
    fun currentTimeMillis() = System.currentTimeMillis()
}