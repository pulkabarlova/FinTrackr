package com.polina.fintrackr.features.expenses

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.polina.fintrackr.R
import com.polina.fintrackr.core.ui.AppScaffold

@Composable
fun ExpensesScreen(navController: NavController) {
    Text("Expenses")
    AppScaffold(
        navController = navController,
        content = {Text("Expenses") },
        topBar = { TopBar() })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,),
        title = {
            Text(
                text = stringResource(id = R.string.expenses),
                color = MaterialTheme.colorScheme.onPrimary
            ) }
    )
}