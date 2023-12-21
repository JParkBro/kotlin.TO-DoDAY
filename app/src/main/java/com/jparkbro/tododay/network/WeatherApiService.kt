package com.jparkbro.tododay.network

import com.jparkbro.tododay.BuildConfig
import com.jparkbro.tododay.model.WeatherDTO
import retrofit2.http.GET
import retrofit2.http.Query

private const val WEATHER_API_KEY = BuildConfig.WEATHER_API_KEY

interface WeatherApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = WEATHER_API_KEY
    ): WeatherDTO
}
