package com.jparkbro.tododay.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

private const val TAG = "BottomNavigationBar"

val bottomItems = listOf(
    NavigationDestination.WeatherView,
    NavigationDestination.TodoView,
    NavigationDestination.DDayView,
    NavigationDestination.SettingView,
)

@Composable
fun BottomNavigationBar(
    modifier : Modifier = Modifier,
    navController: NavController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = modifier
    ) {
        bottomItems.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(0)
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { item.icon?.let { Icon(imageVector = it, contentDescription = item.route) } },
                label = { Text(text = stringResource(item.resourceId)) },
            )
        }
    }
}