package com.jparkbro.tododay.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jparkbro.tododay.BuildConfig
import com.jparkbro.tododay.model.WeatherDTO
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val WEATHER_API_KEY = BuildConfig.WEATHER_API_KEY
private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

private val json = Json { ignoreUnknownKeys = true }

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String = WEATHER_API_KEY
    ): WeatherDTO
}

object WeatherApi {
    val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}