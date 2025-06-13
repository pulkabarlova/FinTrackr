package com.polina.fintrackr.core.navigation
import com.polina.fintrackr.R

object NavBarItems {
    val BarItems = listOf(
        BarItem(R.string.expenses, icon = R.drawable.expenses_icon, route = "expenses"),
        BarItem(R.string.incomes, icon = R.drawable.incoms_icon, route = "income"),
        BarItem(R.string.count, icon = R.drawable.count_icon, route = "count"),
        BarItem(R.string.articles, icon = R.drawable.articles_icon, route = "articles"),
        BarItem(R.string.settings, icon = R.drawable.settings_icon, route = "settings")
    )
}