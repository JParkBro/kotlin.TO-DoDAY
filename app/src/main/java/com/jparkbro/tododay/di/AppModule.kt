package com.jparkbro.tododay.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jparkbro.tododay.data.LocalDatabase
import com.jparkbro.tododay.data.todo.TodoDao
import com.jparkbro.tododay.data.todo.TodoDao_Impl
import com.jparkbro.tododay.data.todo.TodoRepository
import com.jparkbro.tododay.data.weather.WeatherDataSource
import com.jparkbro.tododay.data.weather.WeatherDataSourceImpl
import com.jparkbro.tododay.data.weather.WeatherRepository
import com.jparkbro.tododay.network.WeatherApiService
import com.jparkbro.tododay.utils.LocationLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
private val json = Json { ignoreUnknownKeys = true }

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providesWeatherRetrofit() : Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val client = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLoggingInterceptor)
        }

        client.apply {
            readTimeout(60, TimeUnit.SECONDS)
        }

        return Retrofit.Builder()
            .client(client.build())
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
    @Provides
    @Singleton
    fun providesLocationLiveData(@ApplicationContext context: Context) : LocationLiveData {
        return LocationLiveData(context)
    }
    @Provides
    @Singleton
    fun providesLocalDatabase(@ApplicationContext context: Context) : LocalDatabase {
        return LocalDatabase.getDatabase(context)
    }
    @Provides
    @Singleton
    fun providesTodoDao(localDatabase: LocalDatabase) : TodoDao {
        return localDatabase.todoDao()
    }
    @Provides
    @Singleton
    fun providesTodoRepository(todoDao: TodoDao) : TodoRepository {
        return TodoRepository(todoDao)
    }



}