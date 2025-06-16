package com.polina.fintrackr.core.navigation

sealed class NavRoutes(
    val route: String
) {
    data object Expenses : NavRoutes("expenses")
    data object Income : NavRoutes("income")
    data object Count : NavRoutes("count")
    data object Articles : NavRoutes("articles")
    data object Settings : NavRoutes("settings")
    data object Splash : NavRoutes("splash")
    data object History : NavRoutes("history")
}