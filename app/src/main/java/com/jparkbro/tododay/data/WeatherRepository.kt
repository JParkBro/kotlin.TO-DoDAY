package com.jparkbro.tododay.data

import com.jparkbro.tododay.model.FirebaseDTO
import com.jparkbro.tododay.model.WeatherDTO
import com.jparkbro.tododay.network.WeatherApi

class WeatherRepository {

    suspend fun getWeather(lat: String, lon: String) : WeatherDTO {
        return WeatherApi.retrofitService.getWeather(lat = lat, lon = lon)
    }
    suspend fun getFirebaseData(
        weather: String,
    ) {

    }

}