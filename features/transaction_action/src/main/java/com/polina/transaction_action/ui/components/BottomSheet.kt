package com.polina.transaction_action.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.polina.transaction_action.ui.TransactionAddViewModel
import com.polina.ui.components.ListItem
import com.polina.ui.components.ListItemUi
import com.polina.ui.models.CategoryModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesBottomSheet(
    categories: List<CategoryModel>,
    onCancel: () -> Unit,
    onSelect: (CategoryModel) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = { onCancel() },
        sheetState = sheetState
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(items = categories) { article ->
                ListItemUi(
                    ListItem(
                        title = article.name,
                        leadingIcon = article.emoji
                    ),
                    onClick = { onSelect(article) }
                )
            }
        }
    }
}
