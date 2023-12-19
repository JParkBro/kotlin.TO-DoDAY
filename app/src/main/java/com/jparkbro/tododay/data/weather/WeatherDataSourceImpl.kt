package com.jparkbro.tododay.data.weather

import com.jparkbro.tododay.model.FirebaseDto
import com.jparkbro.tododay.model.WeatherDTO
import com.jparkbro.tododay.network.WeatherApiService
import javax.inject.Inject

class WeatherDataSourceImpl @Inject constructor(
    private val apiService: WeatherApiService
) : WeatherDataSource {

    override suspend fun getWeather(lat: String, lon: String): WeatherDTO {
        return apiService.getWeather(lat = lat, lon = lon)
    }

    override suspend fun getFirebaseData(weather: String): FirebaseDto {
        TODO("Not yet implemented")
    }
}