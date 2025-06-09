package com.polina.fintrackr.features.articles

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
fun ArticlesScreen(navController: NavController) {
    AppScaffold(
        navController = navController,
        content = {Text("Articles") },
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
                text = stringResource(id = R.string.articles),
                color = MaterialTheme.colorScheme.onPrimary
            ) }
    )
}