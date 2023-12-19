package com.jparkbro.tododay.ui.todo

import com.jparkbro.tododay.model.TodoEntity
import com.kizitonwose.calendar.core.yearMonth
import java.time.LocalDate
import java.time.YearMonth

data class TodoUiState(
    val selectedDate: LocalDate = LocalDate.now(),
    val isMonthMode: Boolean = false,

    val todoList: List<TodoEntity> = listOf(),
)