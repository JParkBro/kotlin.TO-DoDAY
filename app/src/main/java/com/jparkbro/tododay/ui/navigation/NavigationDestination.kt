package com.jparkbro.tododay.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.jparkbro.tododay.R

sealed class NavigationDestination(val route: String, @StringRes val resourceId: Int, val icon: ImageVector?) {
    object Info : NavigationDestination("info", R.string.info, null)
    object Weather : NavigationDestination("weather", R.string.weather, null)
    object Todo : NavigationDestination("todo", R.string.todo, null)
    object DDay : NavigationDestination("dday", R.string.d_day, null)
    object Setting : NavigationDestination("setting", R.string.setting, null)
}