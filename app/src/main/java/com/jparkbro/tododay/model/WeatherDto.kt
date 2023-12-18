package com.jparkbro.tododay.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDTO(
    val weather: List<WeatherInfo>,
    val main: Main,
    val name: String,
)

@Serializable
data class WeatherInfo(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val sea_level: Int,
    val grnd_level: Int
)