package com.jparkbro.tododay.ui.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jparkbro.tododay.data.todo.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

private const val TAG = "TODO_VIEW_MODEL"

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private val _uiState = mutableStateOf(TodoUiState())
    val uiState: TodoUiState by _uiState

    init {
        selectDate(LocalDate.now())
    }

    fun toggleMonthMode() {
        _uiState.value = _uiState.value.copy(isMonthMode = !_uiState.value.isMonthMode)
    }

    fun selectDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(selectedDate = date)

        val selectedDate = date.toString()
        getTodos(selectedDate)
    }

    private fun getTodos(date: String) {
        viewModelScope.launch {
            repository.getAllTodosStream(date).collect() { todos ->
                _uiState.value = _uiState.value.copy(todoList = todos)
            }
        }
    }
}