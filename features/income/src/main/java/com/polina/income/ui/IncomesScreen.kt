package com.polina.income.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polina.income.R
import com.polina.ui.models.IncomeModel
import com.polina.ui.navigation.daggerViewModel
import com.polina.ui.navigation.entities.NavRoutes

/**
 * Отвечает за отображение UI и обработку взаимодействия пользователя.
 */
@Composable
fun IncomesScreen(navController: NavController, viewModel: IncomesViewModel = daggerViewModel()) {
    val incomes = viewModel.incomes.value
    val totalIncome = viewModel.totalIncomes.value
    val currency = viewModel.incomes.value.firstOrNull()?.currency ?: " ₽"
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

    com.polina.ui.components.AppScaffold(
        navController = navController,
        content = { paddingValues ->
            Content(
                paddingValues = paddingValues,
                incomes,
                totalIncome,
                currency,
                navController
            )
        },
        topBar = {
            com.polina.ui.components.AppTopBar(
                R.string.my_incomes,
                R.drawable.trailing_icon,
                onTrailingIconClick = { navController.navigate("history_incomes") })
        })
}

@Composable
fun Content(
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    incomes: List<IncomeModel>,
    totalIncome: Double,
    currency: String,
    navController: NavController
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
                com.polina.ui.components.ListItemUi(
                    com.polina.ui.components.ListItem(
                        title = stringResource(R.string.all),
                        trailingText = totalIncome.toString() + currency,
                    ),
                    onClick = {},
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                )
            }
            if (incomes.isNotEmpty()) {
                items(items = incomes) { income ->
                    com.polina.ui.components.ListItemUi(
                        item = com.polina.ui.components.ListItem(
                            title = income.title,
                            trailingText = income.amount.toString() + income.currency,
                            trailingIcon = Icons.Default.KeyboardArrowRight
                        ),
                        onClick = {
                            navController.navigate(NavRoutes.ExpensesEdit.withTransactionId(income.id))
                        }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = {navController.navigate("expenses_add")},
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(0.dp),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}

@Preview(name = "Light Mode", showSystemUi = true)
@Composable
fun Preview() {
    com.polina.ui.theme.FinTrackrTheme {
        IncomesScreen(navController = NavController(LocalContext.current))
    }
}