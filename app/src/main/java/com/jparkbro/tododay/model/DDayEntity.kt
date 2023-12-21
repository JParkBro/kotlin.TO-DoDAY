package com.jparkbro.tododay.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ddays")
data class DDayEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val date: String,
    // TODO image 추가
//    val image: String,
    val state: Boolean /* true = D-Day(날짜 감소), false = 날짜수(날짜 증가) */
)