package com.jparkbro.tododay.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jparkbro.tododay.model.Todo

@Database(entities = [Todo::class, /* DDay::class */], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
//    abstract fun dDayDao(): DDayDao

    companion object {
        @Volatile
        private var Instance: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, LocalDatabase::class.java, "local_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}