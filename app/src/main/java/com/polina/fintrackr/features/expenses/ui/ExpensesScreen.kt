package com.polina.fintrackr.features.expenses.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polina.fintrackr.R
import com.polina.fintrackr.core.theme.FinTrackrTheme
import com.polina.fintrackr.core.ui.AppScaffold
import com.polina.fintrackr.core.ui.ListItem
import com.polina.fintrackr.core.ui.ListItemUi
import com.polina.fintrackr.features.expenses.domain.Expense
import com.polina.fintrackr.features.expenses.domain.SumExpenses

@Composable
fun ExpensesScreen(navController: NavController) {
    val mockExpenses = remember { getMockExpenses() }
    val mockSumExpenses = remember { SumExpenses(sum = 10000, currency = "₽") }
    AppScaffold(
        navController = navController,
        content = { paddingValues ->
            Content(
                paddingValues = paddingValues,
                mockExpenses,
                mockSumExpenses
            )
        },
        topBar = { TopBar() })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.my_expenses),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Center)
                )

                Icon(
                    painter = painterResource(R.drawable.trailing_icon),
                    contentDescription = "trailing_icon",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                )
            }
        }

    )
}

@Composable
fun Content(
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    expenses: List<Expense>,
    sumExpenses: SumExpenses
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
                        trailingText = sumExpenses.sum.toString() + " " + sumExpenses.currency,
                    ),
                    onClick = {},
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                )
            }
            items(items = expenses) { expense ->
                ListItemUi(
                    item = ListItem(
                        title = expense.title,
                        leadingIcon = expense.iconTag,
                        trailingText = expense.trailText + " " + expense.currency,
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

fun getMockExpenses(): List<Expense> {
    return listOf(
        Expense(
            title = "Аренда",
            trailText = "45 000",
            currency = "₽",
            iconTag = "A"
        ),
        Expense(
            title = "Продукты домой",
            trailText = "45 000",
            currency = "₽",
            iconTag = "ПД"
        ),
        Expense(
            title = "Аренда",
            trailText = "45 000",
            currency = "₽",
            iconTag = "А"
        ),
        Expense(
            title = "Аренда жилья",
            trailText = "45 000",
            currency = "₽",
            iconTag = "АЖ"
        )
    )
}

@Preview(name = "Light Mode", showSystemUi = true)
@Composable
fun Preview() {
    FinTrackrTheme {
        ExpensesScreen(navController = NavController(LocalContext.current))
    }
}