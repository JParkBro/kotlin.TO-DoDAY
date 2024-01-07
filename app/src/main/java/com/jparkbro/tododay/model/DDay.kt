package com.jparkbro.tododay.model

data class DDay (
    val id: Int = 0,
    val title: String = "",
    val date: String = "",
    // TODO image 추가
//    val image: String = "",
    val state: Boolean = true, /* true = D-Day(날짜 감소) = 1, false = 날짜수(날짜 증가) = 0 */
)