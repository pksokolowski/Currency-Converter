package com.github.pksokolowski.currencyconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pksokolowski.currencyconverter.backend.server.model.CurrencySubWallet
import com.github.pksokolowski.currencyconverter.domain.ObtainExchangeRateUseCase
import com.github.pksokolowski.currencyconverter.domain.ObtainSubWalletsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val obtainSubWalletsUseCase: ObtainSubWalletsUseCase,
    private val obtainExchangeRateUseCase: ObtainExchangeRateUseCase,
) : ViewModel() {
    // todo use an interface with immutable flows or add private mutable props
    val subWallets = MutableStateFlow(listOf<CurrencySubWallet>())
    val sellInputValue = MutableStateFlow("0.00")
    val buyInputValue = MutableStateFlow("0.00")
    val sellCurrency = MutableStateFlow("EUR")
    val buyCurrency = MutableStateFlow("EUR")

    private val selectedCurrencyExchangeRate = obtainExchangeRateUseCase.getUpdates()
        .combine(buyCurrency) { allRates, selectedCurrencyCode ->
            allRates?.get(selectedCurrencyCode)
        }
        .launchIn(viewModelScope)

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