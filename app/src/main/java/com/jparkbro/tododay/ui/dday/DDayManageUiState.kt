package com.jparkbro.tododay.ui.dday

import com.jparkbro.tododay.model.DDay
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

data class DDayManageUiState(
    val dDay: DDay = DDay(),
    val state: String = "D-DAY",
    val year: Int = LocalDate.now().year,
    val month: Int = LocalDate.now().monthValue,
    val day: Int = LocalDate.now().dayOfMonth,
    val dayOfWeek: String = LocalDate.now().dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN),
    val isEntryValid: Boolean = false
)