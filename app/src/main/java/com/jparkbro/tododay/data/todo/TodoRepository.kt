package com.jparkbro.tododay.data.todo

import android.util.Log
import com.jparkbro.tododay.model.TodoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "TODO_EDIT_VIEW_MODEL"

class TodoRepository @Inject constructor(
    private val todoDao: TodoDao
) {
    suspend fun insertTodo(todoEntity: TodoEntity): Unit {
        todoDao.insert(todoEntity)
    }
    suspend fun updateTodo(todoEntity: TodoEntity): Unit {
        todoDao.update(todoEntity)
    }
    suspend fun deleteTodo(todoEntity: TodoEntity): Unit {
        todoDao.delete(todoEntity)
    }
    fun getTodoStream(id: Int): Flow<TodoEntity?> {
        return todoDao.getTodo(id)
    }
    fun getAllTodosStream(date: String): Flow<List<TodoEntity>> {
        return todoDao.getAllTodos(date)
    }
}