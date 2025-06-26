package com.polina.fintrackr.features.history.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.polina.fintrackr.R
import com.polina.fintrackr.core.ui.theme.FinTrackrTheme
import com.polina.fintrackr.core.ui.components.AppScaffold
import com.polina.fintrackr.core.ui.components.AppTopBar
import com.polina.fintrackr.core.ui.components.ListItem
import com.polina.fintrackr.core.ui.components.ListItemUi
import com.polina.fintrackr.features.expenses.domain.ExpenseModel
import com.polina.fintrackr.features.expenses.ui.TransactionViewModel
import com.polina.fintrackr.features.history.ui.components.CustomDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryExpensesScreen(
    navController: NavController,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val expenses = viewModel.expenses
    val currency = viewModel.expenses.firstOrNull()?.currency ?: " ₽"
    val beginDt = viewModel.formatDateTime(expenses.firstOrNull()?.createdAt)
    val beginDate = beginDt.first
    val endDt = viewModel.formatDateTime(expenses.lastOrNull()?.createdAt)
    val endDate = endDt.first
    val sum = viewModel.totalExpenses
    val error = viewModel.error.value
    val context = LocalContext.current
    val isConnected = viewModel.isConnected.value

    LaunchedEffect(isConnected) {
        if (isConnected && error != null) {
            viewModel.getTransactions()
        }
    }

    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }

    AppScaffold(
        navController = navController,
        content = { paddingValues -> Content(paddingValues, beginDate, endDate, sum, expenses,currency, viewModel)},
        topBar = {
            AppTopBar(R.string.history, R.drawable.texthistory,
                {}, Icons.Default.KeyboardArrowLeft, {navController.popBackStack()})
        })
}

@Composable
fun Content(
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    beginDate: String,
    endDate: String,
    sum: Double,
    expenses: List<ExpenseModel>,
    currency: String,
    viewModel: TransactionViewModel
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var isSelectingStartDate by remember { mutableStateOf(true) }
    var selectedDate by remember { mutableStateOf(System.currentTimeMillis()) }

    val dateFormatter = SimpleDateFormat("dd MMMM", Locale("ru"))

    val formattedStartDate = viewModel.startDate.value?.let {
        dateFormatter.format(it)
    } ?: beginDate

    val formattedEndDate = viewModel.endDate.value?.let {
        dateFormatter.format(it)
    } ?: endDate

    if (showDatePicker) {
        CustomDatePicker(
            selectedDate = selectedDate,
            onDateSelected = { dateMillis ->
                dateMillis?.let {
                    val selected = Date(it)
                    if (isSelectingStartDate) {
                        viewModel.setStartDate(selected)
                    } else {
                        viewModel.setEndDate(selected)
                    }
                }
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                ListItemUi(
                    ListItem(
                        title = stringResource(R.string.start),
                        trailingText = formattedStartDate,
                    ),
                    onClick = {
                        isSelectingStartDate = true
                        selectedDate = viewModel.startDate.value?.time ?: System.currentTimeMillis()
                        showDatePicker = true
                    },
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                )
            }
            item {
                ListItemUi(
                    ListItem(
                        title = stringResource(R.string.end),
                        trailingText = formattedEndDate,
                    ),
                    onClick = {
                        isSelectingStartDate = false
                        selectedDate = viewModel.endDate.value?.time ?: System.currentTimeMillis()
                        showDatePicker = true
                    },
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                )
            }
            item {
                ListItemUi(
                    ListItem(
                        title = stringResource(R.string.summ),
                        trailingText = sum.toString() + currency,
                    ),
                    onClick = {},
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                )
            }
            if (expenses.isNotEmpty()) {
                items(items = expenses) { expense ->
                    val dt = viewModel.formatDateTime(expense.createdAt)
                    ListItemUi(
                        item = ListItem(
                            title = expense.title,
                            leadingIcon = expense.emoji,
                            trailingText = expense.amount.toString() + expense.currency,
                            trailingIcon = Icons.Default.KeyboardArrowRight,
                            trailingBottomText = "${dt.first} ${dt.second}"
                        ),
                        onClick = { }
                    )
                }
            }
        }
    }
}


@Preview(name = "Light Mode", showSystemUi = true)
@Composable
fun Preview() {
    FinTrackrTheme {
        HistoryExpensesScreen(navController = NavController(LocalContext.current))
    }
}