package com.polina.settings.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.polina.settings.R
import com.polina.ui.components.ListItem
import com.polina.ui.components.ListItemUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageBottomSheet(
    onSelectLanguage: (String) -> Unit,
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
                title = stringResource(R.string.language_ru),
                leadingIcon = "RU"
            ),
            onClick = {
                onSelectLanguage("ru")
                onCancel()
            },
        )
        ListItemUi(
            ListItem(
                title = stringResource(R.string.language_en),
                leadingIcon = "EN"
            ),
            onClick = {
                onSelectLanguage("en")
                onCancel()
            },
        )
        ListItemUi(
            ListItem(
                title = stringResource(R.string.language_ge),
                leadingIcon = "GE"
            ),
            onClick = {
                onSelectLanguage("de")
                onCancel()
            },
        )
    }
}