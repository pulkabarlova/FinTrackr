package com.polina.fintrackr.features.history.ui

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
import com.polina.fintrackr.features.expenses.domain.TransactionViewModel

@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val expenses = viewModel.expenses
    val currency = viewModel.expenses.firstOrNull()?.currency ?: " ₽"
    val beginDt = viewModel.formatDateTime(expenses.firstOrNull()?.createdAt)
    val beginDate = beginDt.first
    val beginTim = beginDt.second
    val endDt = viewModel.formatDateTime(expenses.lastOrNull()?.createdAt)
    val endDate = endDt.first
    val endTime = endDt.second
    val sum = viewModel.totalExpenses

    AppScaffold(
        navController = navController,
        content = { paddingValues -> Content(paddingValues, beginDate, beginTim, endDate, endTime, sum, expenses,currency)},
        topBar = {
            AppTopBar(R.string.history, R.drawable.texthistory,
                {}, Icons.Default.KeyboardArrowLeft, {navController.popBackStack()})
        })
}

@Composable
fun Content(
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    beginDate: String,
    beginTim: String,
    endDate: String,
    endTime: String,
    sum: Double,
    expenses: List<ExpenseModel>,
    currency: String
) {
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
                        trailingText = "$beginDate $beginTim",
                    ),
                    onClick = {},
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                )
            }
            item {
                ListItemUi(
                    ListItem(
                        title = stringResource(R.string.end),
                        trailingText = "$endDate $endTime",
                    ),
                    onClick = {},
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
                    ListItemUi(
                        item = ListItem(
                            title = expense.title,
                            leadingIcon = expense.emoji,
                            trailingText = expense.amount.toString() + expense.currency,
                            trailingIcon = Icons.Default.KeyboardArrowRight
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
        HistoryScreen(navController = NavController(LocalContext.current))
    }
}