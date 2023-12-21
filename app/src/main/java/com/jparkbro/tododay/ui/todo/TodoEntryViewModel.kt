package com.jparkbro.tododay.ui.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jparkbro.tododay.data.todo.TodoRepository
import com.jparkbro.tododay.model.Todo
import com.jparkbro.tododay.model.TodoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoEntryViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    var uiState by mutableStateOf(TodoManageUiState())
        private set

    fun updateUiState(todo: Todo) {
        uiState = TodoManageUiState(todo = todo, isEntryValid = validateInput(todo))
    }

    private fun validateInput(todo: Todo = uiState.todo): Boolean {
        return with(todo) {
            title.isNotBlank() && date.isNotBlank()
        }
    }

    suspend fun insertTodo() {
        if (validateInput()) {
            todoRepository.insertTodo(uiState.todo.toTodo())
        }
    }
}

fun Todo.toTodo(): TodoEntity = TodoEntity(
    id = id,
    title = title,
    date = date,
    startTime = startTime,
    endTime = endTime,
    description = description,
)