package com.polina.fintrackr.features.count

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.polina.fintrackr.core.ui.AppScaffold

@Composable
fun CountScreen(navController: NavController) {
    Text("Count")
    AppScaffold(
        navController = navController,
        content = {Text("Count") })
}
