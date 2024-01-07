package com.jparkbro.tododay.data.dday

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jparkbro.tododay.model.DDayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DDayDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: DDayEntity)

    @Update
    suspend fun update(entity: DDayEntity)

    @Delete
    suspend fun delete(entity: DDayEntity)

    @Query("SELECT * FROM ddays WHERE id = :id")
    fun getDDay(id: Int): Flow<DDayEntity>

    @Query("SELECT * FROM ddays")
    fun getAllDDays(): Flow<List<DDayEntity>>
}