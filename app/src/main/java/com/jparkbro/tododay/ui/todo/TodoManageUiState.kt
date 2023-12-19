package com.jparkbro.tododay.ui.todo

import com.jparkbro.tododay.model.Todo

data class TodoManageUiState(
    val todo: Todo = Todo(),
    val isEntryValid: Boolean = false
)