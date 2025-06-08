package com.polina.fintrackr.features.articles

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.polina.fintrackr.core.ui.AppScaffold

@Composable
fun ArticlesScreen(navController: NavController) {
    //Text("Articles")
    AppScaffold(
        navController = navController,
        content = {Text("Articles") })
}