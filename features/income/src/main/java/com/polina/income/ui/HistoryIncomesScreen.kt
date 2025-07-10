package com.polina.income.ui

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.polina.income.R
import com.polina.income.ui.components.CustomDatePicker
import com.polina.ui.components.AppScaffold
import com.polina.ui.components.AppTopBar
import com.polina.ui.components.ListItem
import com.polina.ui.components.ListItemUi
import com.polina.ui.models.IncomeModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Отвечает за отображение UI и обработку взаимодействия пользователя.
 */
@Composable
fun HistoryIncomesScreen(
    navController: NavController,
    viewModel: IncomesViewModel = hiltViewModel()
) {
    val incomes = viewModel.incomes.value
    val currency = viewModel.incomes.value.firstOrNull()?.currency ?: " ₽"
    val beginDt = viewModel.formatDateTime(incomes.firstOrNull()?.createdAt)
    val beginDate = beginDt.first
    val endDt = viewModel.formatDateTime(incomes.lastOrNull()?.createdAt)
    val endDate = endDt.first
    val sum = viewModel.totalIncomes.value
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
        content = { paddingValues ->
            ContentIncomes(
                paddingValues,
                beginDate,
                endDate,
                sum,
                incomes,
                currency.toString(),
                viewModel
            )
        },
        topBar = {
            AppTopBar(
                R.string.history, R.drawable.texthistory,
                {}, Icons.Default.KeyboardArrowLeft, { navController.popBackStack() })
        })
}


@Composable
fun ContentIncomes(
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    beginDate: String,
    endDate: String,
    sum: Double,
    incomes: List<IncomeModel>,
    currency: String,
    viewModel: IncomesViewModel
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
                    onClick = { showDatePicker = true },
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
                        title = stringResource(R.string.summ),
                        trailingText = sum.toString() + currency,
                    ),
                    onClick = {
                        isSelectingStartDate = false
                        selectedDate = viewModel.endDate.value?.time ?: System.currentTimeMillis()
                        showDatePicker = true
                    },
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                )
            }
            if (incomes.isNotEmpty()) {
                items(items = incomes) { income ->
                    val dt = viewModel.formatDateTime(income.createdAt)
                    ListItemUi(
                        item = ListItem(
                            title = income.title,
                            leadingIcon = income.emoji,
                            trailingText = income.amount.toString() + income.currency,
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
