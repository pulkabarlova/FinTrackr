package com.polina.fintrackr.features.expenses

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.polina.fintrackr.core.ui.AppScaffold

@Composable
fun ExpensesScreen(navController: NavController) {
    Text("Expenses")
    AppScaffold(
        navController = navController,
        content = {Text("Expenses") })
}