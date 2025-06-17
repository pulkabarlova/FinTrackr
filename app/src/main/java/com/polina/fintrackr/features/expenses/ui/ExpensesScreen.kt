package com.polina.fintrackr.features.expenses.ui

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polina.fintrackr.R
import com.polina.fintrackr.core.domain.model.transaction.Transaction
import com.polina.fintrackr.core.generateMockData
import com.polina.fintrackr.core.theme.FinTrackrTheme
import com.polina.fintrackr.core.ui.AppScaffold
import com.polina.fintrackr.core.ui.AppTopBar
import com.polina.fintrackr.core.ui.ListItem
import com.polina.fintrackr.core.ui.ListItemUi

@Composable
fun ExpensesScreen(navController: NavController) {
    val mockExpenses = remember { getMockTransactions() }
    val mockSumExpenses = remember { mockExpenses.map { it.amount }.sum() }
    AppScaffold(
        navController = navController,
        content = { paddingValues ->
            Content(
                paddingValues = paddingValues,
                mockExpenses,
                mockSumExpenses
            )
        },
        topBar = {
            AppTopBar(
                R.string.my_expenses,
                R.drawable.trailing_icon,
                onTrailingIconClick = { navController.navigate("history") })
        })
}


@Composable
fun Content(
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    expenses: List<Transaction>,
    sumExpenses: Int
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
                        trailingText = sumExpenses.toString() + " ₽",
                    ),
                    onClick = {},
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                )
            }
            items(items = expenses) { expense ->
                ListItemUi(
                    item = ListItem(
                        title = expense.category.name,
                        leadingIcon = expense.category.emoji,
                        trailingText = expense.amount.toString() + " ₽",
                        trailingIcon = Icons.Default.KeyboardArrowRight
                    ),
                    onClick = { }
                )
            }
        }

        FloatingActionButton(
            onClick = {},
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


fun getMockTransactions(): List<Transaction> {
    val transactions = generateMockData()
    val expenseTransactions = transactions.filter { !it.category.isIncome }
    return expenseTransactions
}

@Preview(name = "Light Mode", showSystemUi = true)
@Composable
fun Preview() {
    FinTrackrTheme {
        ExpensesScreen(navController = NavController(LocalContext.current))
    }
}