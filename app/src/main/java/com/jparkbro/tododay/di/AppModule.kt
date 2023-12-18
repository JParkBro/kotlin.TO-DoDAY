package com.jparkbro.tododay.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jparkbro.tododay.data.WeatherDataSource
import com.jparkbro.tododay.data.WeatherDataSourceImpl
import com.jparkbro.tododay.data.WeatherRepository
import com.jparkbro.tododay.network.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
private val json = Json { ignoreUnknownKeys = true }

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providesWeatherRetrofit() : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .build()
    }
    @Provides
    @Singleton
    fun providesWeatherApiService(retrofit: Retrofit) : WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }
    @Provides
    @Singleton
    fun providesWeatherDataSource(apiService: WeatherApiService) : WeatherDataSource {
        return WeatherDataSourceImpl(apiService)
    }
    @Provides
    @Singleton
    fun providesWeatherRepository(weatherDataSource: WeatherDataSource) : WeatherRepository {
        return WeatherRepository(weatherDataSource)
    }

}