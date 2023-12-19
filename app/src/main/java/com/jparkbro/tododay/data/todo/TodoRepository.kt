package com.jparkbro.tododay.data.todo

import com.jparkbro.tododay.model.TodoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

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