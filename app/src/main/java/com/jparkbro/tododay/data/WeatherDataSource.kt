package com.jparkbro.tododay.data

import com.jparkbro.tododay.model.FirebaseDto
import com.jparkbro.tododay.model.WeatherDTO


interface WeatherDataSource {

    suspend fun getWeather(lat: String, lon: String) : WeatherDTO
    suspend fun getFirebaseData(weather: String) : FirebaseDto

}
/*

class WeatherRepository {

    suspend fun getWeather(lat: String, lon: String) : WeatherDTO {
        return WeatherApi.retrofitService.getWeather(lat = lat, lon = lon)
    }
    suspend fun getFirebaseData(
        weather: String,
    ) {

    }

}*/
