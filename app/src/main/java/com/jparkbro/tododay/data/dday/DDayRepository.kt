package com.jparkbro.tododay.data.dday

import com.jparkbro.tododay.model.DDayEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DDayRepository @Inject constructor(
    private val dao: DDayDao
) {
    suspend fun insertDDay(entity: DDayEntity): Unit {
        dao.insert(entity)
    }
    suspend fun updateTodo(entity: DDayEntity): Unit {
        dao.update(entity)
    }
    suspend fun deleteTodo(entity: DDayEntity): Unit {
        dao.delete(entity)
    }
    fun getTodoStream(id: Int): Flow<DDayEntity?> {
        return dao.getDDay(id)
    }
    fun getAllTodosStream(): Flow<List<DDayEntity>> {
        return dao.getAllDDays()
    }
}
