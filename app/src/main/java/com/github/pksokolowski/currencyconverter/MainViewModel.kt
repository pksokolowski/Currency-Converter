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

    init {
        viewModelScope.launch {
            subWallets.value = obtainSubWalletsUseCase.fetchSubWallets() ?: listOf()
        }
    }

    fun setSellInputValue(newValue: String) {
        sellInputValue.value = newValue
    }
}