package com.polina.fintrackr

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polina.fintrackr.core.navigation.NavRoutes
import com.polina.fintrackr.core.theme.FinTrackrTheme
import com.polina.fintrackr.features.articles.ArticlesScreen
import com.polina.fintrackr.features.count.CountScreen
import com.polina.fintrackr.features.expenses.ExpensesScreen
import com.polina.fintrackr.features.incoms.IncomesScreen
import com.polina.fintrackr.features.settings.SettingsScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppUi() {
    val navController = rememberNavController()
    SharedTransitionLayout {
        NavHost(
            navController,
            startDestination = NavRoutes.Expenses.route
        ) {
            composable(NavRoutes.Expenses.route) {
                ExpensesScreen(navController)
            }
            composable(NavRoutes.Income.route) {
                IncomesScreen(navController)
            }
            composable(NavRoutes.Count.route) {
                CountScreen(navController)
            }
            composable(NavRoutes.Articles.route) {
                ArticlesScreen(navController)
            }
            composable(NavRoutes.Settings.route) {
                SettingsScreen(navController)
            }
        }
    }
}
