package com.jparkbro.tododay.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jparkbro.tododay.ui.dday.DDayDetailScreen
import com.jparkbro.tododay.ui.dday.DDayEditScreen
import com.jparkbro.tododay.ui.dday.DDayScreen
import com.jparkbro.tododay.ui.info.InfoScreenFirst
import com.jparkbro.tododay.ui.info.InfoScreenSecond
import com.jparkbro.tododay.ui.info.InfoScreenThird
import com.jparkbro.tododay.ui.setting.SettingScreen
import com.jparkbro.tododay.ui.todo.TodoEntryScreen
import com.jparkbro.tododay.ui.todo.TodoScreen
import com.jparkbro.tododay.ui.weather.WeatherScreen

private const val TAG = "NAV_GRAPH"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TododayNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationDestination.Todo.route, // TODO Preference 에 Info 봤는지 추가, 봤으면 Weather 화면으로 바로 이동
    ) {
        infoGraph(navController = navController)
        composable(route = NavigationDestination.WeatherView.route) { WeatherScreen() }
        todoGraph(navController = navController)
        ddayGraph(navController = navController)
        composable(route = NavigationDestination.SettingView.route) { SettingScreen() }
    }
}

fun NavGraphBuilder.infoGraph(navController: NavController) {
    navigation(startDestination = NavigationDestination.InfoFirst.route, route = NavigationDestination.Info.route) {
        composable(
            route = NavigationDestination.InfoFirst.route,
            enterTransition = {
                if (initialState.destination.route == NavigationDestination.InfoFirst.route) {
                    slideInHorizontally { it }
                } else {
                    slideInHorizontally { -it }
                }
            },
            exitTransition = {
                slideOutHorizontally { -it }
            }
        ) {
            InfoScreenFirst(
                navigateToNext = { navController.navigate(NavigationDestination.InfoSecond.route) { popUpTo(0) } }
            )
        }
        composable(
            route = NavigationDestination.InfoSecond.route,
            enterTransition = {
                if (initialState.destination.route == NavigationDestination.InfoFirst.route) {
                    slideInHorizontally { it }
                } else {
                    slideInHorizontally { -it }
                }
            },
            exitTransition = {
                if (targetState.destination.route == NavigationDestination.InfoThird.route) {
                    slideOutHorizontally { -it }
                } else {
                    slideOutHorizontally { it }
                }
            }
        ) {
            InfoScreenSecond(
                navigateToNext = { navController.navigate(NavigationDestination.InfoThird.route) { popUpTo(0) } },
                navigateToBefore = { navController.navigate(NavigationDestination.InfoFirst.route) { popUpTo(0) } }

            )
        }
        composable(
            route = NavigationDestination.InfoThird.route,
            enterTransition = {
                if (initialState.destination.route == NavigationDestination.InfoSecond.route) {
                    slideInHorizontally { it }
                } else {
                    slideInHorizontally { -it }
                }
            },
            exitTransition = {
                if (targetState.destination.route == NavigationDestination.WeatherView.route) {
                    slideOutHorizontally { -it }
                } else {
                    slideOutHorizontally { it }
                }
            }
        ) {
            InfoScreenThird(
                navigateToNext = { navController.navigate(NavigationDestination.WeatherView.route) { popUpTo(0) } },
                navigateToBefore = { navController.navigate(NavigationDestination.InfoSecond.route) { popUpTo(0) } }
            )
        }
    }
}

fun NavGraphBuilder.todoGraph(navController: NavController) {
    navigation(startDestination = NavigationDestination.TodoView.route, route= NavigationDestination.Todo.route) {
        composable(route = NavigationDestination.TodoView.route) {
            TodoScreen(
                navigateToItemEdit = { navController.navigate(NavigationDestination.TodoEdit.route) }
            )
        }
        composable(route = NavigationDestination.TodoEdit.route) {
            TodoEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}

fun NavGraphBuilder.ddayGraph(navController: NavController) {
    navigation(startDestination = NavigationDestination.DDayView.route, route= NavigationDestination.DDay.route) {
        composable(route = NavigationDestination.DDayView.route) {
            DDayScreen()
        }
        composable(route = NavigationDestination.DDayEdit.route) {
            DDayEditScreen()
        }
        composable(route = NavigationDestination.DDayDetail.route) {
            DDayDetailScreen()
        }
    }
}