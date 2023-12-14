package com.jparkbro.tododay.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jparkbro.tododay.ui.dday.DDayScreen
import com.jparkbro.tododay.ui.info.InfoScreen
import com.jparkbro.tododay.ui.setting.SettingScreen
import com.jparkbro.tododay.ui.todo.TodoScreen
import com.jparkbro.tododay.ui.weather.WeatherScreen

@Composable
fun TododayNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationDestination.Info.route,
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) }
    ) {
        composable(NavigationDestination.Info.route) { InfoScreen() }
        composable(NavigationDestination.Weather.route) { WeatherScreen() }
        composable(NavigationDestination.Todo.route) { TodoScreen() }
        composable(NavigationDestination.DDay.route) { DDayScreen() }
        composable(NavigationDestination.Setting.route) { SettingScreen() }
    }
}