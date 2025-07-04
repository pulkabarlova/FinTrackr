package com.polina.fintrackr.app

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.polina.fintrackr.core.ui.navigation.entities.NavRoutes
import com.polina.fintrackr.features.splash.ui.SplashScreen
import com.polina.fintrackr.features.articles.ui.ArticlesScreen
import com.polina.fintrackr.features.count.ui.CountEditScreen
import com.polina.fintrackr.features.count.ui.CountScreen
import com.polina.fintrackr.features.expenses.ui.ExpensesScreen
import com.polina.fintrackr.features.history.ui.HistoryExpensesScreen
import com.polina.fintrackr.features.history.ui.HistoryIncomesScreen
import com.polina.fintrackr.features.incoms.ui.IncomesScreen
import com.polina.fintrackr.features.settings.SettingsScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppUi() {
    val navController = rememberNavController()
    SharedTransitionLayout {
        NavHost(
            navController,
            startDestination = NavRoutes.Splash.route
        ) {
            composable(NavRoutes.Splash.route) {
                SplashScreen(navController)
            }
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
            composable(NavRoutes.HistoryExpenses.route) {
                HistoryExpensesScreen(navController)
            }
            composable(NavRoutes.HistoryIncomes.route) {
                HistoryIncomesScreen(navController)
            }
            composable(NavRoutes.CountEdit.route) {
                CountEditScreen(navController)
            }
        }
    }
}