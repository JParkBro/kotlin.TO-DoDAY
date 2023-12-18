package com.jparkbro.tododay.ui.todo

import com.jparkbro.tododay.model.Todo
import java.time.LocalDate

data class TodoUiState(
    val currentData: LocalDate = LocalDate.now(),
    val selectedDate: LocalDate? = LocalDate.now(),
    val isMonthMode: Boolean = false,

    val todoList: List<Todo> = listOf(),

)