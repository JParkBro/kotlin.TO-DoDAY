package com.jparkbro.tododay.ui.todo

import androidx.lifecycle.ViewModel
import com.jparkbro.tododay.data.TodoRepository

private const val TAG = "TODO_VIEW_MODEL"

class TodoViewModel(
    private val repository: TodoRepository
) : ViewModel() {

}