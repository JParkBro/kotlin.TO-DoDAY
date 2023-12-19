package com.jparkbro.tododay.data.todo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jparkbro.tododay.model.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todoEntity: TodoEntity)

    @Update
    suspend fun update(todoEntity: TodoEntity)

    @Delete
    suspend fun delete(todoEntity: TodoEntity)

    @Query("SELECT * FROM todos WHERE id = :id")
    fun getTodo(id: Int): Flow<TodoEntity>

    @Query("SELECT * FROM todos WHERE date = :date ORDER BY startTime ASC")
    fun getAllTodos(date: String): Flow<List<TodoEntity>>
}