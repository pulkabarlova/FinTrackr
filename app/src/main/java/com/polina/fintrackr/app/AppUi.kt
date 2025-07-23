package com.polina.fintrackr.app

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.polina.ui.navigation.entities.NavRoutes
import com.polina.splash.ui.SplashScreen
import com.polina.articles.ui.ArticlesScreen
import com.polina.count.ui.CountEditScreen
import com.polina.count.ui.CountScreen
import com.polina.expenses.ui.ExpensesScreen
import com.polina.expenses.ui.HistoryExpensesScreen
import com.polina.income.ui.HistoryIncomesScreen
import com.polina.income.ui.IncomesScreen
import com.polina.settings.ui.SettingsScreen
import com.polina.settings.ui.SettingsViewModel
import com.polina.transaction_action.ui.ExpensesAnalysScreen
import com.polina.transaction_action.ui.IncomesAnalysScreen
import com.polina.transaction_action.ui.TransactionAddScreen
import com.polina.transaction_action.ui.TransactionEditScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppUi(settingsViewModel: SettingsViewModel) {
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
                SettingsScreen(navController, settingsViewModel)
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
            composable(NavRoutes.ExpensesAdd.route) {
                TransactionAddScreen(navController)
            }
            composable(NavRoutes.ExpensesAnal.route) {
                ExpensesAnalysScreen(navController)
            }
            composable(NavRoutes.IncomesAnal.route) {
                IncomesAnalysScreen(navController)
            }
            composable(
                NavRoutes.ExpensesEdit.route,
                arguments = listOf(
                    navArgument("transactionId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val transactionId = backStackEntry.arguments?.getInt("transactionId") ?: -1
                TransactionEditScreen(navController, transactionId)
            }
        }
    }
}