package com.jparkbro.tododay.data.weather

import com.jparkbro.tododay.data.weather.WeatherDataSource
import com.jparkbro.tododay.model.WeatherDTO
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherDataSource: WeatherDataSource
) {
    suspend fun getWeather(lat: String, lon: String) : WeatherDTO {
        return weatherDataSource.getWeather(lat = lat, lon = lon)
    }
}