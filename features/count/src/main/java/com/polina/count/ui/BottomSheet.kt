package com.polina.count.ui

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.polina.count.R

import com.polina.ui.components.ListItem
import com.polina.ui.components.ListItemUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyBottomSheet(
    onSelectCurrency: (String) -> Unit,
    onCancel: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = {
            onCancel()
        },
        sheetState = sheetState
    ) {
        ListItemUi(
            ListItem(
                title = stringResource(R.string.ruble),
                leadingIcon = "\u20BD"
            ),
            onClick = {
                onSelectCurrency("₽")
            },
        )
        ListItemUi(
            ListItem(
                title = stringResource(R.string.dollar),
                leadingIcon = "\u0024"
            ),
            onClick = {
                onSelectCurrency("$")
            },
        )
        ListItemUi(
            ListItem(
                title = stringResource(R.string.euro),
                leadingIcon = "\u20AC"
            ),
            onClick = {
                onSelectCurrency("€")
            },
        )
        ListItemUi(
            ListItem(
                title = stringResource(R.string.cancel),
                leadingIcon = Icons.Default.Close,
            ),
            modifier = Modifier.background(MaterialTheme.colorScheme.error),
            onClick = {
                onCancel()
            },
        )
    }
}