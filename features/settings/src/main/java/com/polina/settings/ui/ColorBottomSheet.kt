package com.polina.settings.ui


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.polina.settings.R
import com.polina.ui.components.ListItem
import com.polina.ui.components.ListItemUi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorBottomSheet(
    onSelectTheme: (String) -> Unit,
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
                title = stringResource(R.string.green),
                leadingIcon = "\uD83D\uDFE2"
            ),
            onClick = {
                onSelectTheme("green")
            },
        )
        ListItemUi(
            ListItem(
                title = stringResource(R.string.pink),
                leadingIcon = "\uD83D\uDFE3"
            ),
            onClick = {
                onSelectTheme("pink")
            },
        )
        ListItemUi(
            ListItem(
                title = stringResource(R.string.blue),
                leadingIcon = "\uD83D\uDD35"
            ),
            onClick = {
                onSelectTheme("blue")
            },
        )
    }
}