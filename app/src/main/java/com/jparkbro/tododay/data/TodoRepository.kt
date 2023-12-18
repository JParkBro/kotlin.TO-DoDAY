package com.jparkbro.tododay.data

import com.jparkbro.tododay.model.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {

    suspend fun insertTodo(todo: Todo) = todoDao.insert(todo)

    suspend fun updateTodo(todo: Todo) = todoDao.update(todo)

    suspend fun deleteTodo(todo: Todo) = todoDao.delete(todo)

    fun getTodoStream(id: Int): Flow<Todo?> = todoDao.getTodo(id)

    fun getAllTodosStream(date: String): Flow<List<Todo>> = todoDao.getAllTodos(date)
}