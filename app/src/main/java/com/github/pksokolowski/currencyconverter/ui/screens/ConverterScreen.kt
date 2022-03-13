package com.github.pksokolowski.currencyconverter.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.pksokolowski.currencyconverter.MainViewModel
import com.github.pksokolowski.currencyconverter.R
import com.github.pksokolowski.currencyconverter.ui.views.CurrencyAndAmountForm
import com.github.pksokolowski.currencyconverter.ui.theme.Purple700

@Composable
fun ConverterScreen(
    viewModel: MainViewModel = viewModel()
) {
    val subWallets = viewModel.subWallets.collectAsState()

    val sellInputValue = viewModel.sellInputValue.collectAsState()
    val buyInputValue = viewModel.buyInputValue.collectAsState()

    val sellCurrencySelected = viewModel.sellCurrency.collectAsState()
    val buyCurrencySelected = viewModel.buyCurrency.collectAsState()

    Column() {
        Text(
            text = stringResource(R.string.label_my_balances),
            fontSize = 10.sp,
            modifier = Modifier.padding(8.dp),
            color = colorResource(id = R.color.faded_label)
        )
        LazyRow(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            items(subWallets.value) { wallet ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = wallet.amount.toString() + " ${wallet.currencyCode}")
                }
            }
        }

        Text(
            text = stringResource(R.string.label_exchange),
            fontSize = 10.sp,
            modifier = Modifier.padding(8.dp),
            color = colorResource(id = R.color.faded_label)
        )

        CurrencyAndAmountForm(
            icon = painterResource(R.drawable.ic_sell_24),
            color = Color.Red,
            label = stringResource(id = R.string.label_sell),
            amount = sellInputValue.value,
            availableCurrencies = subWallets.value.map { it.currencyCode },
            onAmountChange = viewModel::setSellInputValue,
            selectedCurrency = sellCurrencySelected.value,
            onSelectedCurrencyChange = viewModel::setSellCurrency,
        )

        CurrencyAndAmountForm(
            icon = painterResource(R.drawable.ic_buy_24),
            color = Color.Green,
            label = stringResource(id = R.string.label_buy),
            amount = buyInputValue.value,
            availableCurrencies = subWallets.value.map { it.currencyCode },
            onAmountChange = viewModel::setBuyInputValue,
            selectedCurrency = buyCurrencySelected.value,
            onSelectedCurrencyChange = viewModel::setBuyCurrency,
        )

        OutlinedButton(
            modifier = Modifier.align(CenterHorizontally),
            onClick = { },
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
    }
}