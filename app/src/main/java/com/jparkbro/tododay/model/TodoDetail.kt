package com.jparkbro.tododay.model

data class TodoDetail(
    val id: Int = 0,
    val title: String = "",
    val date: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val description: String = "",
)
