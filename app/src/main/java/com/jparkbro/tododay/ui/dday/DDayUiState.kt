package com.jparkbro.tododay.ui.dday

import com.jparkbro.tododay.model.DDayEntity

data class DDayUiState(
    val viewList: DDayViewList = DDayViewList.ALL,
    val sortType: DDaySortType = DDaySortType.D_DAY,

    val dDayList: List<DDayEntity> = listOf(),
)

enum class DDayViewList {
    ALL, DATE_COUNT, D_DAY
}

/*
 * D_DAY = D-1 , D-2, ... 1일, 2일
 * DATE_COUNT = 100일, 99일, ... D-2, D-1
 */
enum class DDaySortType {
    D_DAY, DATE_COUNT
}