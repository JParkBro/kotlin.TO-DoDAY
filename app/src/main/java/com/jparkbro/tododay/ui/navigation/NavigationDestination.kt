package com.jparkbro.tododay.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChecklistRtl
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector
import com.jparkbro.tododay.R

sealed interface NavigationDestination {
    val route: String
    val resourceId: Int
    val icon: ImageVector?
}
sealed class TododayDestination : NavigationDestination {
    object Info : TododayDestination() {
        override val route: String = "info"
        override val resourceId: Int = R.string.info
        override val icon: ImageVector? = null
    }
    object InfoFirst : TododayDestination() {
        override val route: String = "infoFist"
        override val resourceId: Int = R.string.info_first
        override val icon: ImageVector? = null
    }
    object InfoSecond : TododayDestination() {
        override val route: String = "infoSecond"
        override val resourceId: Int = R.string.info_second
        override val icon: ImageVector? = null
    }
    object InfoThird : TododayDestination() {
        override val route: String = "infoThird"
        override val resourceId: Int = R.string.info_third
        override val icon: ImageVector? = null
    }

    object WeatherView : TododayDestination() {
        override val route: String = "weatherView"
        override val resourceId: Int = R.string.weather_view
        override val icon: ImageVector = Icons.Default.WbSunny
    }

    object Todo : TododayDestination() {
        override val route: String = "todo"
        override val resourceId: Int = R.string.todo
        override val icon: ImageVector? = null
    }
    object TodoView : TododayDestination() {
        override val route: String = "todoView"
        override val resourceId: Int = R.string.todo_view
        override val icon: ImageVector = Icons.Default.ChecklistRtl
    }
    object TodoEntry : TododayDestination() {
        override val route: String = "todoEntry"
        override val resourceId: Int = R.string.todo_entry
        override val icon: ImageVector? = null
    }
    object TodoEdit : TododayDestination() {
        override val route: String = "todoEdit"
        override val resourceId: Int = R.string.todo_edit
        override val icon: ImageVector? = null
        const val todoIdArg = "todoId"
        val routeWithArgs = "$route/{$todoIdArg}"
    }

    object DDay : TododayDestination() {
        override val route: String = "dday"
        override val resourceId: Int = R.string.d_day
        override val icon: ImageVector? = null
    }
    object DDayView : TododayDestination() {
        override val route: String = "ddayView"
        override val resourceId: Int = R.string.d_day_view
        override val icon: ImageVector = Icons.Default.DateRange
    }
    object DDayEntry : TododayDestination() {
        override val route: String = "ddayEntry"
        override val resourceId: Int = R.string.d_day_entry
        override val icon: ImageVector? = null
    }
    object DDayEdit : TododayDestination() {
        override val route: String = "ddayEdit"
        override val resourceId: Int = R.string.d_day_edit
        override val icon: ImageVector? = null
        const val ddayIdArg = "ddayId"
        val routeWithArgs = "$route/{$ddayIdArg}"
    }
    object DDayDetail : TododayDestination() {
        override val route: String = "ddayDetail"
        override val resourceId: Int = R.string.d_day_detail
        override val icon: ImageVector? = null
        const val ddayIdArg = "ddayId"
        val routeWithArgs = "$route/{$ddayIdArg}"
    }

    object SettingView : TododayDestination() {
        override val route: String = "setting"
        override val resourceId: Int = R.string.setting_view
        override val icon: ImageVector = Icons.Default.Settings
    }
}