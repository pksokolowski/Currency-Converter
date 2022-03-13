package com.github.pksokolowski.currencyconverter.ui.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> Spinner(
    items: List<T>,
    selectedItem: T?,
    onSelectedItemChanged: (T) -> Unit,
    drawItem: @Composable (T?) -> Unit,
    modifier: Modifier = Modifier,
    dropdownArrowContentDescription: String = ""
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .clickable {
                expanded = !expanded
            },
        contentAlignment = Alignment.CenterEnd
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            drawItem(selectedItem)
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = dropdownArrowContentDescription
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onSelectedItemChanged(item)
                        }
                    ) {
                        drawItem(item)
                    }
                }
            }
        }
    }
}