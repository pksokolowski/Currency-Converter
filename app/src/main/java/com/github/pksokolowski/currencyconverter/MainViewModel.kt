package com.github.pksokolowski.currencyconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pksokolowski.currencyconverter.backend.server.model.CurrencySubWallet
import com.github.pksokolowski.currencyconverter.domain.ObtainExchangeRateUseCase
import com.github.pksokolowski.currencyconverter.domain.ObtainSubWalletsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val obtainSubWalletsUseCase: ObtainSubWalletsUseCase,
    obtainExchangeRateUseCase: ObtainExchangeRateUseCase,
) : ViewModel() {

    //<editor-fold defaultstate="collapsed" desc="private, mutable props">
    private val _subWallets = MutableStateFlow(listOf<CurrencySubWallet>())
    private val _sellInputValue = MutableStateFlow("0.00")
    private val _buyInputValue = MutableStateFlow("0.00")
    private val _sellCurrency = MutableStateFlow("EUR")
    private val _buyCurrency = MutableStateFlow("EUR")
    //</editor-fold>

    val subWallets = _subWallets.asStateFlow()
    val sellInputValue = _sellInputValue.asStateFlow()
    val buyInputValue = _buyInputValue.asStateFlow()
    val sellCurrency = _sellCurrency.asStateFlow()
    val buyCurrency = _buyCurrency.asStateFlow()

    init {
        fetchSubWallets()

        obtainExchangeRateUseCase.getUpdates()
            .combine(buyCurrency) { allRates, selectedCurrencyCode ->
                allRates?.get(selectedCurrencyCode)
            }
            .launchIn(viewModelScope)
    }

    private fun fetchSubWallets() {
        viewModelScope.launch {
            _subWallets.value = obtainSubWalletsUseCase.fetchSubWallets() ?: listOf()
        }
    }

    fun setSellInputValue(newValue: String) {
        if (newValue.count { it == '.' } > 1) return
        _sellInputValue.value = newValue.filter {
            it.isDigit() || it == '.'
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun setBuyInputValue(newValue: String) {
        // this is an illegal action, nothing needs to be changed
        // however, an error message might be shown
    }

    fun setSellCurrency(currencyCode: String) {
        _sellCurrency.value = currencyCode
    }

    fun setBuyCurrency(currencyCode: String) {
        _buyCurrency.value = currencyCode
    }
}