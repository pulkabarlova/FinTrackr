package com.polina.transaction_action.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polina.transaction_action.R
import com.polina.transaction_action.ui.components.ArticlesBottomSheet
import com.polina.transaction_action.ui.components.CustomTimePicker
import com.polina.ui.components.AppScaffold
import com.polina.ui.components.AppTopBar
import com.polina.ui.components.CustomDatePicker
import com.polina.ui.components.ListItem
import com.polina.ui.components.ListItemUi
import com.polina.ui.navigation.daggerViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * Отвечает за отображение/редактирование UI и обработку взаимодействия пользователя.
 */
@Composable
fun TransactionEditScreen(
    navController: NavController,
    transactionId: Int,
    viewModel: TransactionEditViewModel = daggerViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(transactionId) {
        viewModel.fetchTransaction(transactionId)
    }
    AppScaffold(
        navController = navController,
        content = {
            ContentEdit(
                paddingValues = it,
                viewModel,
                navController,
                transactionId
            )
        },
        topBar = {
            AppTopBar(
                R.string.edit_transaction,
                isLeading = Icons.Default.Close,
                isTrailing = Icons.Default.Check,
                onBackIconClick = { navController.popBackStack()},
                onTrailingIconClick = {
                    viewModel.updateTransactionById(transactionId,
                        onSuccess = {
                            navController.popBackStack()
                        },
                        onError = { errorMessage ->
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            )
        })
}

@Composable
fun ContentEdit(
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    viewModel: TransactionEditViewModel,
    navController: NavController,
    transactionId: Int
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showArticleSheet by remember { mutableStateOf(false) }
    var selectedArticle by remember { mutableStateOf<String?>(null) }

    val selectedDate = viewModel.selectedDate.collectAsState().value
    val selectedTime = viewModel.selectedTime.collectAsState().value
    val amount by viewModel.amount.collectAsState()
    val comment by viewModel.comment.collectAsState()
    val categoryId by viewModel.categoryId.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val transaction by viewModel.transaction.collectAsState()

    val formattedDate = remember(selectedDate) {
        selectedDate?.let {
            SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(it))
        } ?: ""
    }

    val formattedTime = remember(selectedTime) {
        selectedTime?.let { String.format("%02d:%02d", it.first, it.second) } ?: ""
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ListItemUi(
                item = ListItem(
                    title = stringResource(R.string.article),
                    trailingText = selectedArticle ?: transaction?.category?.name,
                    trailingIcon = Icons.Default.KeyboardArrowRight,
                ),
                onClick = { showArticleSheet = true }
            )
            TextField(
                value = amount?.toString() ?: transaction?.amount.toString(),
                onValueChange = { newValue ->
                    viewModel.setAmount(newValue)
                },
                placeholder = {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = stringResource(R.string.summa),
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.5.dp, MaterialTheme.colorScheme.surfaceContainer),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            )

            ListItemUi(
                item = ListItem(
                    title = stringResource(R.string.date),
                    trailingText = formattedDate,
                ),
                onClick = { showDatePicker = true }
            )

            ListItemUi(
                item = ListItem(
                    title = stringResource(R.string.time),
                    trailingText = formattedTime,
                ),
                onClick = { showTimePicker = true }
            )
            TextField(
                value = comment ?: transaction?.comment.toString(),
                onValueChange = { newValue ->
                    viewModel.setComment(newValue)
                },
                placeholder = {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = stringResource(R.string.comment),
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.5.dp, MaterialTheme.colorScheme.surfaceContainer),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )
            ListItemUi(
                ListItem(
                    title = stringResource(R.string.delete),
                    leadingIcon = Icons.Default.Close,
                ),
                modifier = Modifier.background(MaterialTheme.colorScheme.error),
                onClick = {
                    viewModel.deleteTransaction(transactionId)
                    navController.popBackStack()
                },
            )
        }
        if (showDatePicker) {
            CustomDatePicker(
                selectedDate = selectedDate ?: System.currentTimeMillis(),
                onDateSelected = {
                    if (it != null) {
                        viewModel.setDate(it)
                    }
                    showDatePicker = false
                },
                onDismiss = { showDatePicker = false }
            )
        }
        if (showTimePicker) {
            CustomTimePicker(
                onTimeSelected = { hour, minute ->
                    viewModel.setTime(hour, minute)
                    showTimePicker = false
                },
                onDismiss = { showTimePicker = false }
            )
        }
        if (showArticleSheet) {
            ArticlesBottomSheet(
                categories = categories,
                onCancel = { showArticleSheet = false },
                onSelect = { article ->
                    selectedArticle = article.name
                    showArticleSheet = false
                    viewModel.setCategoryId(article.id)
                }
            )
        }
    }
}