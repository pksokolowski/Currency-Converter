package com.github.pksokolowski.currencyconverter.ui.utils

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

abstract class TextMessage {
    @Composable
    abstract fun getString(): String
}

class ResIdTextMessage(@StringRes private val resId: Int) : TextMessage() {
    @Composable
    override fun getString(): String {
        return stringResource(id = resId)
    }
}

class LiteralTextMessage(private val message: String) : TextMessage() {
    @Composable
    override fun getString(): String {
        return message
    }
}