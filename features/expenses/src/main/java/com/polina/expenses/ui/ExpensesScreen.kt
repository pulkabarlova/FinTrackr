package com.polina.expenses.ui

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.polina.expenses.R
import com.polina.ui.components.AppScaffold
import com.polina.ui.components.AppTopBar
import com.polina.ui.components.ListItem
import com.polina.ui.components.ListItemUi
import com.polina.ui.models.ExpenseModel
import com.polina.ui.navigation.entities.NavRoutes

/**
 * Отвечает за отображение UI и обработку взаимодействия пользователя.
 */
@Composable
fun ExpensesScreen(
    navController: NavController,
    viewModel: ExpensesViewModel = hiltViewModel()
) {
    var expenses = viewModel.expenses.value
    var totalExpenses = viewModel.totalExpenses.value
    var currency = viewModel.expenses.value.firstOrNull()?.currency ?: " ₽"
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
            Content(
                paddingValues = paddingValues,
                expenses,
                totalExpenses,
                currency,
                navController
            )
        },
        topBar = {
            AppTopBar(
                R.string.my_expenses,
                R.drawable.trailing_icon,
                onTrailingIconClick = { navController.navigate("history_expenses") })
        })
}


@Composable
fun Content(
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    expenses: List<ExpenseModel>,
    sumExpenses: Double,
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
                ListItemUi(
                    ListItem(
                        title = stringResource(R.string.all),
                        trailingText = sumExpenses.toString() + currency,
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
                        onClick = {
                            navController.navigate(NavRoutes.ExpensesEdit.withTransactionId(expense.id))
                        }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("expenses_add") },
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