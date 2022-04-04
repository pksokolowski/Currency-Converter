package com.github.pksokolowski.currencyconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pksokolowski.currencyconverter.backend.model.CurrencySubWallet
import com.github.pksokolowski.currencyconverter.common.computeExchangeValue
import com.github.pksokolowski.currencyconverter.domain.ObtainExchangeRateUseCase
import com.github.pksokolowski.currencyconverter.domain.ObtainUserDataUseCase
import com.github.pksokolowski.currencyconverter.domain.PerformExchangeUseCase
import com.github.pksokolowski.currencyconverter.ui.utils.LiteralTextMessage
import com.github.pksokolowski.currencyconverter.ui.utils.ResIdTextMessage
import com.github.pksokolowski.currencyconverter.ui.utils.TextMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val obtainUserDataUseCase: ObtainUserDataUseCase,
    private val performExchangeUseCase: PerformExchangeUseCase,
    obtainExchangeRateUseCase: ObtainExchangeRateUseCase,
) : ViewModel() {

    //<editor-fold defaultstate="collapsed" desc="private, mutable props">
    private val _subWallets = MutableStateFlow(listOf<CurrencySubWallet>())
    private val _sellAmount = MutableStateFlow("0.00")
    private val _sellCurrency = MutableStateFlow("EUR")
    private val _buyCurrency = MutableStateFlow("USD")
    private val _message = MutableStateFlow<TextMessage?>(null)
    private var _buyAmount = "0.00"
    private var _commissionStatus =
        MutableStateFlow<TextMessage>(ResIdTextMessage(R.string.message_commission_status_loading))
    //</editor-fold>

    val subWallets = _subWallets.asStateFlow()
    val sellAmount = _sellAmount.asStateFlow()
    val sellCurrency = _sellCurrency.asStateFlow()
    val buyCurrency = _buyCurrency.asStateFlow()

    val message = _message.asStateFlow()
    val commissionStatus = _commissionStatus.asStateFlow()

    val buyAmount = combine(
        obtainExchangeRateUseCase.getUpdates(),
        sellCurrency,
        buyCurrency,
        sellAmount,
    ) { allRates, sellCurrencyValue, buyCurrencyValue, sellAmountValue ->
        if (allRates == null) return@combine null
        val sellCurrencyRate = allRates[sellCurrencyValue] ?: return@combine null
        val buyCurrencyRate = allRates[buyCurrencyValue] ?: return@combine null
        computeExchangeValue(sellAmountValue, sellCurrencyRate, buyCurrencyRate).toString()
    }.onEach { _buyAmount = it.toString() }

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        viewModelScope.launch {
            _subWallets.value = obtainUserDataUseCase.fetchSubWallets() ?: listOf()
        }
        viewModelScope.launch {
            val response = obtainUserDataUseCase.fetchCommissionStatusMessage()?.let {
                LiteralTextMessage(it)
            }
                ?: ResIdTextMessage(R.string.message_failed_to_fetch_commission_status)
            _commissionStatus.value = response
        }
    }

    fun setSellAmount(newValue: String) {
        // enforce input format for amount to be sold
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
        _commissionStatus.value = ResIdTextMessage(R.string.message_commission_status_loading)

        viewModelScope.launch {
            val transactionStatus = performExchangeUseCase.exchange(
                sellAmount = sellAmount.value,
                sellCurrencyCode = sellCurrency.value,
                buyAmount = _buyAmount,
                buyCurrencyCode = buyCurrency.value
            )

            _message.value = LiteralTextMessage(
                transactionStatus.message
            )

            if (transactionStatus.accepted) {
                fetchUserData()
            }
        }
    }

    fun dismissMessage() {
        _message.value = null
    }
}