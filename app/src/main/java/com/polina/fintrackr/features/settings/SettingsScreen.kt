package com.polina.fintrackr.features.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.polina.fintrackr.core.ui.AppScaffold

@Composable
fun SettingsScreen(navController: NavController) {
    Text("Settings")
    AppScaffold(
        navController = navController,
        content = {Text("Settings") })
}


