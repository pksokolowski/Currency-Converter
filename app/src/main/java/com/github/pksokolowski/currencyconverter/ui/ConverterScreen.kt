package com.github.pksokolowski.currencyconverter.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.pksokolowski.currencyconverter.MainViewModel
import com.github.pksokolowski.currencyconverter.R
import com.github.pksokolowski.currencyconverter.ui.theme.Purple700

@Composable
fun ConverterScreen(
    viewModel: MainViewModel = viewModel()
) {
    val subWallets = viewModel.subWallets.collectAsState()
    val sellInputValue = viewModel.sellInputValue.collectAsState()
    val isOpen = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf("") }

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
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, Color.Transparent),
                    contentPadding = PaddingValues(0.dp),  //avoid the little icon
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White,
                        backgroundColor = Color.Red
                    )
                ) {
                    Icon(
                        painterResource(R.drawable.ic_sell_24),
                        contentDescription = "content description"
                    )
                }
                Text(
                    text = stringResource(id = R.string.label_sell),
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BasicTextField(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                    value = sellInputValue.value,
                    onValueChange = viewModel::setSellInputValue
                )
                Spinner(
                    listOf("abc", "def"),
                    selectedItem.value,
                    { selectedItem.value = it },
                    { item ->
                        Text(item ?: "")
                    },
                    modifier = Modifier
                        .width(64.dp),
                )
            }
        }

        Row(
            Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { },
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                border = BorderStroke(1.dp, Color.Transparent),
                contentPadding = PaddingValues(0.dp),  //avoid the little icon
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White,
                    backgroundColor = Color.Green
                )
            ) {
                Icon(
                    painterResource(R.drawable.ic_buy_24),
                    contentDescription = "content description"
                )
            }
            Text(
                text = stringResource(id = R.string.label_buy),
                modifier = Modifier.padding(8.dp)
            )
        }

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