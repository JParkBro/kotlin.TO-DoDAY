package com.jparkbro.tododay.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChecklistRtl
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector
import com.jparkbro.tododay.R

sealed class NavigationDestination(val route: String, @StringRes val resourceId: Int, val icon: ImageVector?) {
    object Info : NavigationDestination("info", R.string.info, null)
    object InfoFirst : NavigationDestination("infoFist", R.string.info_first, null)
    object InfoSecond : NavigationDestination("infoSecond", R.string.info_second, null)
    object InfoThird : NavigationDestination("infoThird", R.string.info_third, null)

    object WeatherView : NavigationDestination("weatherView", R.string.weather_view, Icons.Default.WbSunny)

    object Todo : NavigationDestination("todo", R.string.todo, Icons.Default.ChecklistRtl)
    object TodoView : NavigationDestination("todoView", R.string.todo_view, Icons.Default.ChecklistRtl)
    object TodoEntry : NavigationDestination("todoEntry", R.string.todo_entry, null)
    object TodoEdit : NavigationDestination("todoEdit", R.string.todo_edit, null)

    object DDay : NavigationDestination("dday", R.string.d_day, Icons.Default.DateRange)
    object DDayView : NavigationDestination("ddayView", R.string.d_day_view, Icons.Default.DateRange)
    object DDayEntry : NavigationDestination("ddayEntry", R.string.d_day_entry, null)
    object DDayEdit : NavigationDestination("ddayEdit", R.string.d_day_edit, null)
    object DDayDetail : NavigationDestination("ddayDetail", R.string.d_day_detail, null)

    object SettingView : NavigationDestination("setting", R.string.setting_view, Icons.Default.Settings)
}