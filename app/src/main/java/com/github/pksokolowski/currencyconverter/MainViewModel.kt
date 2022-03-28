package com.github.pksokolowski.currencyconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pksokolowski.currencyconverter.backend.server.model.CurrencySubWallet
import com.github.pksokolowski.currencyconverter.common.computeExchangeValue
import com.github.pksokolowski.currencyconverter.domain.ObtainExchangeRateUseCase
import com.github.pksokolowski.currencyconverter.domain.ObtainSubWalletsUseCase
import com.github.pksokolowski.currencyconverter.domain.PerformExchangeUseCase
import com.github.pksokolowski.currencyconverter.ui.utils.LiteralTextMessage
import com.github.pksokolowski.currencyconverter.ui.utils.TextMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val obtainSubWalletsUseCase: ObtainSubWalletsUseCase,
    private val performExchangeUseCase: PerformExchangeUseCase,
    obtainExchangeRateUseCase: ObtainExchangeRateUseCase,
) : ViewModel() {

    //<editor-fold defaultstate="collapsed" desc="private, mutable props">
    private val _subWallets = MutableStateFlow(listOf<CurrencySubWallet>())
    private val _sellAmount = MutableStateFlow("0.00")
    private val _buyAmount = MutableStateFlow("0.00")
    private val _sellCurrency = MutableStateFlow("EUR")
    private val _buyCurrency = MutableStateFlow("USD")

    private val _message = MutableStateFlow<TextMessage?>(null)
    //</editor-fold>

    val subWallets = _subWallets.asStateFlow()
    val sellAmount = _sellAmount.asStateFlow()
    val buyAmount = _buyAmount.asStateFlow()
    val sellCurrency = _sellCurrency.asStateFlow()
    val buyCurrency = _buyCurrency.asStateFlow()

    val message = _message.asStateFlow()

    init {
        fetchSubWallets()

        combine(
            obtainExchangeRateUseCase.getUpdates(),
            sellCurrency,
            buyCurrency,
            sellAmount,
        ) { allRates, sellCurrencyValue, buyCurrencyValue, sellAmountValue ->
            if (allRates == null) return@combine null
            val sellCurrencyRate = allRates[sellCurrencyValue] ?: return@combine null
            val buyCurrencyRate = allRates[buyCurrencyValue] ?: return@combine null
            computeExchangeValue(sellAmountValue, sellCurrencyRate, buyCurrencyRate)
        }
            .onEach { _buyAmount.value = it.toString() }
            .launchIn(viewModelScope)
    }

    private fun fetchSubWallets() {
        viewModelScope.launch {
            _subWallets.value = obtainSubWalletsUseCase.fetchSubWallets() ?: listOf()
        }
    }

    fun setSellAmount(newValue: String) {
        if (newValue.count { it == '.' } > 1) return
        val dotIndex = newValue.indexOf(".")
        _sellAmount.value = newValue.filterIndexed { i, char ->
            i < dotIndex + 3 && (char.isDigit() || char == '.')
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun setBuyAmount(newValue: String) {
        // this is an illegal action, nothing needs to be changed
        // however, an error message might be shown
    }

    fun setSellCurrency(currencyCode: String) {
        _sellCurrency.value = currencyCode
    }

    fun setBuyCurrency(currencyCode: String) {
        _buyCurrency.value = currencyCode
    }

    fun submitTransaction() {
        viewModelScope.launch {
            val transactionStatus = performExchangeUseCase.exchange(
                sellAmount = sellAmount.value,
                sellCurrencyCode = sellCurrency.value,
                buyAmount = buyAmount.value,
                buyCurrencyCode = buyCurrency.value
            )

            _message.value = LiteralTextMessage(
                transactionStatus.message
            )

            if (transactionStatus.accepted) {
                fetchSubWallets()
            }
        }
    }

    fun dismissMessage() {
        _message.value = null
    }
}