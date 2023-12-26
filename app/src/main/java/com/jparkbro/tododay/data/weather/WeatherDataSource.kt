package com.jparkbro.tododay.data.weather

import com.jparkbro.tododay.model.FirebaseDto
import com.jparkbro.tododay.model.WeatherDTO


interface WeatherDataSource {
    suspend fun getWeather(lat: String, lon: String) : WeatherDTO
    suspend fun getFirebaseData(weather: String) : FirebaseDto
}