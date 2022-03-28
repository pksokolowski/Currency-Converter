package com.github.pksokolowski.currencyconverter.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.pksokolowski.currencyconverter.MainViewModel
import com.github.pksokolowski.currencyconverter.R
import com.github.pksokolowski.currencyconverter.ui.theme.Purple700
import com.github.pksokolowski.currencyconverter.ui.views.CurrencyAndAmountForm

@Composable
fun ConverterScreen(
    viewModel: MainViewModel = viewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val subWallets = viewModel.subWallets.collectAsState()
    val sellInputValue = viewModel.sellAmount.collectAsState()
    val sellCurrencySelected = viewModel.sellCurrency.collectAsState()
    val buyCurrencySelected = viewModel.buyCurrency.collectAsState()
    val exchangeOperationStatus = viewModel.message.collectAsState()
    val buyInputValue = remember(viewModel.buyAmount, lifecycleOwner) {
        viewModel.buyAmount.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }.collectAsState("0.00")

    Column {
        Text(
            text = stringResource(R.string.label_my_balances),
            fontSize = 10.sp,
            modifier = Modifier.padding(8.dp),
        )
        LazyRow(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            items(subWallets.value) { wallet ->
                Column(
                    horizontalAlignment = CenterHorizontally,
                ) {
                    Text(text = wallet.amount.toString() + " ${wallet.currencyCode}")
                }
            }
        }

        Text(
            text = stringResource(R.string.label_exchange),
            fontSize = 10.sp,
            modifier = Modifier.padding(8.dp),
        )

        CurrencyAndAmountForm(
            icon = painterResource(R.drawable.ic_sell_24),
            color = Color.Red,
            label = stringResource(id = R.string.label_sell),
            amount = sellInputValue.value,
            availableCurrencies = subWallets.value.map { it.currencyCode },
            onAmountChange = viewModel::setSellAmount,
            selectedCurrency = sellCurrencySelected.value,
            onSelectedCurrencyChange = viewModel::setSellCurrency,
        )

        CurrencyAndAmountForm(
            icon = painterResource(R.drawable.ic_buy_24),
            color = Color.Green,
            label = stringResource(id = R.string.label_buy),
            amount = buyInputValue.value ?: "0.00",
            availableCurrencies = subWallets.value.map { it.currencyCode },
            onAmountChange = viewModel::setBuyAmount,
            selectedCurrency = buyCurrencySelected.value,
            onSelectedCurrencyChange = viewModel::setBuyCurrency,
        )

        OutlinedButton(
            modifier = Modifier.align(CenterHorizontally),
            onClick = viewModel::submitTransaction,
            border = BorderStroke(1.dp, Color.LightGray),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = Purple700,
                contentColor = Color.White
            )
        ) {
            Text(
                text = stringResource(id = R.string.button_submit_exchange),
                modifier = Modifier.padding(32.dp, 0.dp)
            )
        }

        exchangeOperationStatus.value?.let { status ->
            AlertDialog(
                properties = DialogProperties(
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false,
                    securePolicy = SecureFlagPolicy.Inherit
                ),
                onDismissRequest = viewModel::dismissMessage,
                buttons = {
                    Button(
                        modifier = Modifier.align(CenterHorizontally),
                        onClick = viewModel::dismissMessage
                    ) {
                        Text(text = stringResource(id = R.string.button_ok))
                    }
                },
                text = { Text(status.getString()) }
            )
        }
    }
}