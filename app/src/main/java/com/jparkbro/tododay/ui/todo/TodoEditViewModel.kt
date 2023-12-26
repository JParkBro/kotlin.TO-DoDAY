package com.jparkbro.tododay.ui.todo

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jparkbro.tododay.data.todo.TodoRepository
import com.jparkbro.tododay.model.Todo
import com.jparkbro.tododay.model.TodoEntity
import com.jparkbro.tododay.ui.navigation.TododayDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "TODO_EDIT_VIEW_MODEL"

@HiltViewModel
class TodoEditViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var uiState by mutableStateOf(TodoManageUiState())
        private set

    private val todoId: Int = checkNotNull(savedStateHandle[TododayDestination.TodoEdit.todoIdArg])

    private fun validateInput(todo: Todo = uiState.todo): Boolean {
        return with(todo) {
            title.isNotBlank() && date.isNotBlank()
        }
    }

    init {
        viewModelScope.launch {
            uiState = todoRepository.getTodoStream(todoId)
                .filterNotNull()
                .first()
                .toTodoUiState(isEntryValid = true)
        }
    }

    fun updateUiState(todo: Todo) {
        uiState = TodoManageUiState(todo = todo, isEntryValid = validateInput(todo))
    }

    suspend fun updateTodo() {
        if (validateInput(uiState.todo)) {
            todoRepository.updateTodo(uiState.todo.toTodo())
        }
    }
}

fun TodoEntity.toTodoDetails(): Todo = Todo(
    id = id,
    title = title,
    date = date,
    startTime = startTime,
    endTime = endTime,
    description = description,
)

fun TodoEntity.toTodoUiState(isEntryValid: Boolean = false): TodoManageUiState = TodoManageUiState(
    todo = this.toTodoDetails(),
    isEntryValid = isEntryValid
)