package com.polina.fintrackr.features.incoms

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
fun IncomesScreen(navController: NavController) {
    Text("Incomes")
    AppScaffold(
        navController = navController,
        content = {Text("Incomes") },
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
                text = stringResource(id = R.string.incomes),
                color = MaterialTheme.colorScheme.onPrimary
            ) }
    )
}
