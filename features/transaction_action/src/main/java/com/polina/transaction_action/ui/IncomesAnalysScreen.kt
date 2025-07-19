package com.polina.transaction_action.ui

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
import androidx.navigation.NavController
import com.polina.domain.use_case.CategorySummary
import com.polina.domain.use_case.IncomeCategorySummary
import com.polina.income.ui.IncomesViewModel
import com.polina.transaction_action.R
import com.polina.ui.components.AppScaffold
import com.polina.ui.components.AppTopBar
import com.polina.ui.components.CustomDatePicker
import com.polina.ui.components.ListItem
import com.polina.ui.components.ListItemUi
import com.polina.ui.models.IncomeModel
import com.polina.ui.navigation.daggerViewModel
import com.polina.ui.navigation.entities.NavRoutes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun IncomesAnalysScreen(
    navController: NavController,
    viewModel: IncomesAnalysViewModel = daggerViewModel()
) {
    val categoriesSum = viewModel.categoriesSum.value
    val currency = " ₽"
    val sum = viewModel.totalSum.value
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
            ContentIncomesAnalys(
                paddingValues, sum, categoriesSum,
                currency.toString(), viewModel
            )
        },
        topBar = {
            AppTopBar(
                R.string.analys, null,
                {}, Icons.Default.KeyboardArrowLeft, { navController.popBackStack() })
        })
}

@Composable
fun ContentIncomesAnalys(
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    sum: Double,
    categoriesSum: List<IncomeCategorySummary>,
    currency: String,
    viewModel: IncomesAnalysViewModel,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var isSelectingStartDate by remember { mutableStateOf(true) }
    var selectedDate by remember { mutableStateOf(System.currentTimeMillis()) }

    val dateFormatter = SimpleDateFormat("dd MMMM", Locale("ru"))

    val formattedStartDate = viewModel.startDate.value?.let {
        dateFormatter.format(it)
    } ?: dateFormatter.format(Date())

    val formattedEndDate = viewModel.endDate.value?.let {
        dateFormatter.format(it)
    } ?: dateFormatter.format(Date())

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
            if (categoriesSum.isNotEmpty()) {
                items(items = categoriesSum) { summary ->
                    ListItemUi(
                        item = ListItem(
                            title = summary.categoryName,
                            leadingIcon = summary.icon,
                            trailingText = summary.amount.toString() + currency,
                            trailingIcon = Icons.Default.KeyboardArrowRight,
                            trailingBottomText = summary.percentage.toString() + "%"
                        ),
                        onClick = {
                        }
                    )
                }
            }
        }
    }
}
