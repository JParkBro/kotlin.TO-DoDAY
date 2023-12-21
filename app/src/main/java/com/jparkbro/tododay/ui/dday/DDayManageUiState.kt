package com.jparkbro.tododay.ui.dday

data class DDayManageUiState(
    val id: Int = 0,
    val title: String = "",
    val image: String = "", // TODO image 추가
    val type: DDayType = DDayType.D_DAY,
    val date: String = "",
)
