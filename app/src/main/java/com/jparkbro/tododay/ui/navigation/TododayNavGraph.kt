package com.jparkbro.tododay.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.jparkbro.tododay.R
import com.jparkbro.tododay.ui.dday.DDayDetailScreen
import com.jparkbro.tododay.ui.dday.DDayEditScreen
import com.jparkbro.tododay.ui.dday.DDayEntryScreen
import com.jparkbro.tododay.ui.dday.DDayScreen
import com.jparkbro.tododay.ui.info.InfoScreenFirst
import com.jparkbro.tododay.ui.info.InfoScreenSecond
import com.jparkbro.tododay.ui.info.InfoScreenThird
import com.jparkbro.tododay.ui.setting.SettingScreen
import com.jparkbro.tododay.ui.todo.TodoEditScreen
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
        startDestination = TododayDestination.DDay.route, // TODO Preference 에 Info 봤는지 추가, 봤으면 Weather 화면으로 바로 이동
    ) {
        infoGraph(navController = navController)
        composable(route = TododayDestination.WeatherView.route) { WeatherScreen() }
        todoGraph(navController = navController)
        ddayGraph(navController = navController)
        composable(route = TododayDestination.SettingView.route) { SettingScreen() }
    }
}

fun NavGraphBuilder.infoGraph(navController: NavController) {
    navigation(startDestination = TododayDestination.InfoFirst.route, route = TododayDestination.Info.route) {
        composable(
            route = TododayDestination.InfoFirst.route,
            enterTransition = {
                if (initialState.destination.route == TododayDestination.InfoFirst.route) {
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
                navigateToNext = { navController.navigate(TododayDestination.InfoSecond.route) { popUpTo(0) } }
            )
        }
        composable(
            route = TododayDestination.InfoSecond.route,
            enterTransition = {
                if (initialState.destination.route == TododayDestination.InfoFirst.route) {
                    slideInHorizontally { it }
                } else {
                    slideInHorizontally { -it }
                }
            },
            exitTransition = {
                if (targetState.destination.route == TododayDestination.InfoThird.route) {
                    slideOutHorizontally { -it }
                } else {
                    slideOutHorizontally { it }
                }
            }
        ) {
            InfoScreenSecond(
                navigateToNext = { navController.navigate(TododayDestination.InfoThird.route) { popUpTo(0) } },
                navigateToBefore = { navController.navigate(TododayDestination.InfoFirst.route) { popUpTo(0) } }

            )
        }
        composable(
            route = TododayDestination.InfoThird.route,
            enterTransition = {
                if (initialState.destination.route == TododayDestination.InfoSecond.route) {
                    slideInHorizontally { it }
                } else {
                    slideInHorizontally { -it }
                }
            },
            exitTransition = {
                if (targetState.destination.route == TododayDestination.WeatherView.route) {
                    slideOutHorizontally { -it }
                } else {
                    slideOutHorizontally { it }
                }
            }
        ) {
            InfoScreenThird(
                navigateToNext = { navController.navigate(TododayDestination.WeatherView.route) { popUpTo(0) } },
                navigateToBefore = { navController.navigate(TododayDestination.InfoSecond.route) { popUpTo(0) } }
            )
        }
    }
}

fun NavGraphBuilder.todoGraph(navController: NavController) {
    navigation(startDestination = TododayDestination.TodoView.route, route= TododayDestination.Todo.route) {
        composable(route = TododayDestination.TodoView.route) {
            TodoScreen(
                navigateToTodoEdit = { navController.navigate(TododayDestination.TodoEntry.route) },
                navigateToTodoUpdate = {
                    navController.navigate("${TododayDestination.TodoEdit.route}/$it")
                }
            )
        }
        composable(route = TododayDestination.TodoEntry.route) {
            TodoEntryScreen(
                modifier = Modifier.fillMaxSize(),
                title = stringResource(id = R.string.todo_entry),
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(
            route = TododayDestination.TodoEdit.routeWithArgs,
            arguments = listOf(navArgument(TododayDestination.TodoEdit.todoIdArg) { type = NavType.IntType} )
        ) {
            TodoEditScreen(
                modifier = Modifier.fillMaxSize(),
                title = stringResource(id = R.string.todo_edit),
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }
    }
}

fun NavGraphBuilder.ddayGraph(navController: NavController) {
    navigation(startDestination = TododayDestination.DDayView.route, route= TododayDestination.DDay.route) {
        composable(route = TododayDestination.DDayView.route) {
            DDayScreen(
                navigateToDDayEdit = { navController.navigate(TododayDestination.DDayEntry.route) }
            )
        }
        composable(route = TododayDestination.DDayEntry.route) {
            DDayEntryScreen(
                modifier = Modifier.fillMaxSize(),
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
            )
        }
        composable(route = TododayDestination.DDayDetail.route) {
            DDayDetailScreen()
        }
    }
}