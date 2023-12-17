package com.jparkbro.tododay.model

import com.jparkbro.tododay.R

data class Weather (
    val temp: String = "",
    val weather: Int = R.string.weather_default,
    val location: String = "",

    val description: String = "",
    val image: String = "",

    val month: Int = R.string.month_default,
    val dayOfWeek: Int = R.string.day_default,
    val day: String = "",
)
