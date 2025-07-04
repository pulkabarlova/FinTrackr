package com.polina.fintrackr.core.ui.navigation.entities

/**
 * Реализует навигацию (пути) в приложении
 */

sealed class NavRoutes(
    val route: String
) {
    data object Expenses : NavRoutes("expenses")
    data object Income : NavRoutes("income")
    data object Count : NavRoutes("count")
    data object Articles : NavRoutes("articles")
    data object Settings : NavRoutes("settings")
    data object Splash : NavRoutes("splash")
    data object HistoryExpenses : NavRoutes("history_expenses")
    data object HistoryIncomes : NavRoutes("history_incomes")
    data object CountEdit : NavRoutes ("count_edit")
}