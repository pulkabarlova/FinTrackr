package com.polina.fintrackr.app

import Vibrate
import android.os.Vibrator
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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
import com.polina.data.db.CryptoPref
import com.polina.expenses.ui.ExpensesScreen
import com.polina.expenses.ui.HistoryExpensesScreen
import com.polina.income.ui.HistoryIncomesScreen
import com.polina.income.ui.IncomesScreen
import com.polina.settings.ui.SettingsScreen
import com.polina.settings.ui.SettingsViewModel
import com.polina.settings.ui.components.SetPinScreen
import com.polina.transaction_action.ui.ExpensesAnalysScreen
import com.polina.transaction_action.ui.IncomesAnalysScreen
import com.polina.transaction_action.ui.TransactionAddScreen
import com.polina.transaction_action.ui.TransactionEditScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppUi(settingsViewModel: SettingsViewModel, isVibrationEnables: String = "true") {
    val navController = rememberNavController()
    SharedTransitionLayout {
        val context = LocalContext.current
        NavHost(
            navController,
            startDestination = NavRoutes.Splash.route
        ) {
            composable(NavRoutes.Splash.route) {
                SplashScreen(navController, CryptoPref(context))
            }
            composable(NavRoutes.SetPinScreen.route) {
                SetPinScreen(
                    { navController.navigate("expenses") { popUpTo("pin") { inclusive = true } } },
                    CryptoPref(context), "splash", isVibrationEnables
                )
            }
            composable(NavRoutes.Expenses.route) {
                Vibrate(context, isVibrationEnables)
                ExpensesScreen(navController)
            }
            composable(NavRoutes.Income.route) {
                Vibrate(context, isVibrationEnables)
                IncomesScreen(navController)
            }
            composable(NavRoutes.Count.route) {
                Vibrate(context, isVibrationEnables)
                CountScreen(navController)
            }
            composable(NavRoutes.Articles.route) {
                Vibrate(context, isVibrationEnables)
                ArticlesScreen(navController)
            }
            composable(NavRoutes.Settings.route) {
                Vibrate(context, isVibrationEnables)
                SettingsScreen(navController, settingsViewModel, isVibrationEnables)
            }
            composable(NavRoutes.HistoryExpenses.route) {
                Vibrate(context, isVibrationEnables)
                HistoryExpensesScreen(navController)
            }
            composable(NavRoutes.HistoryIncomes.route) {
                Vibrate(context, isVibrationEnables)
                HistoryIncomesScreen(navController)
            }
            composable(NavRoutes.CountEdit.route) {
                Vibrate(context, isVibrationEnables)
                CountEditScreen(navController)
            }
            composable(NavRoutes.ExpensesAdd.route) {
                Vibrate(context, isVibrationEnables)
                TransactionAddScreen(navController)
            }
            composable(NavRoutes.ExpensesAnal.route) {
                Vibrate(context, isVibrationEnables)
                ExpensesAnalysScreen(navController)
            }
            composable(NavRoutes.IncomesAnal.route) {
                Vibrate(context, isVibrationEnables)
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
                Vibrate(context, isVibrationEnables)
                TransactionEditScreen(navController, transactionId)
            }
        }
    }
}