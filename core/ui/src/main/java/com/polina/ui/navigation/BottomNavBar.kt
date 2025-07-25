package com.polina.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.polina.ui.navigation.entities.NavBarItems
/**
 * Реализует навигацию (ui) в приложении
 */
@Composable
fun BottomNavBar(navController: NavController) {
    NavigationBar() {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        NavBarItems.BarItems.forEach {
            val selected = currentRoute == it.route
            NavigationBarItem(
                selected = selected,
                icon = {
                    Icon(
                        painter =
                        painterResource(it.icon), contentDescription = stringResource(it.title),
                    )
                },
                label = {
                    Text(text = stringResource(it.title), maxLines = 1,
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis)
                },
                onClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }

                },
                colors =
                NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}