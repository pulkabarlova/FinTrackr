package com.polina.fintrackr.features.incoms

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.polina.fintrackr.core.ui.AppScaffold

@Composable
fun IncomesScreen(navController: NavController) {
    Text("Incomes")
    AppScaffold(
        navController = navController,
        content = {Text("Incomes") })
}