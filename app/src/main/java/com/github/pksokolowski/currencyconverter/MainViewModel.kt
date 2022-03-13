package com.github.pksokolowski.currencyconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pksokolowski.currencyconverter.backend.server.model.CurrencySubWallet
import com.github.pksokolowski.currencyconverter.domain.ObtainSubWalletsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val obtainSubWalletsUseCase: ObtainSubWalletsUseCase
) : ViewModel() {
    val subWallets = MutableStateFlow(listOf<CurrencySubWallet>())
    val sellInputValue = MutableStateFlow("0.00")
    val buyInputValue = MutableStateFlow("0.00")
    val sellCurrency = MutableStateFlow("EUR")
    val buyCurrency = MutableStateFlow("EUR")

    init {
        viewModelScope.launch {
            subWallets.value = obtainSubWalletsUseCase.fetchSubWallets() ?: listOf()
        }
    }

    fun setSellInputValue(newValue: String) {
        if (newValue.count { it == '.' } > 1) return
        sellInputValue.value = newValue.filter {
            it.isDigit() || it == '.'
        }
    }

    fun setBuyInputValue(newValue: String) {
        // this is an illegal action, nothing needs to be changed
        // however, an error message might be shown
    }

    fun setSellCurrency(currencyCode: String) {
        sellCurrency.value = currencyCode
    }

    fun setBuyCurrency(currencyCode: String) {
        buyCurrency.value = currencyCode
    }
}