package com.jparkbro.tododay.ui.weather

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.jparkbro.tododay.R
import com.jparkbro.tododay.data.WeatherRepository
import com.jparkbro.tododay.model.LocationDetails
import com.jparkbro.tododay.model.Weather
import java.time.LocalDate
import kotlin.math.roundToInt

private const val TAG = "WEATHER_VIEW_MODEL"

class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepository()
) : ViewModel() {

    private var currentLocation: LocationDetails? = null

    var uiState by mutableStateOf(WeatherUiState())
        private set

    fun fetchLocationData(location: LocationDetails) {
        currentLocation = location
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getWeather() {
        currentLocation?.let {
            try {
                val localDate: LocalDate = LocalDate.now()

                val month = localDate.month.toString()
                val dayOfWeek = localDate.dayOfWeek.toString()
                val day = localDate.dayOfMonth.toString()

                Log.d(TAG, dayOfWeek)
                Log.d(TAG, month)

                repository.getWeather(lat = it.latitude, lon = it.longitude)
                    .let { data ->
                        val weatherDescription = data.weather.firstOrNull()?.main ?: ""
                        val temperature = (data.main.temp - 273.15).roundToInt().toString()
                        val locationName = data.name

                        val weatherResId = getWeatherResourceId(weatherDescription)
                        val monthResId = getMonthResourceId(month)
                        val dayResId = getDayResourceId(dayOfWeek)

                        val weather = Weather(
                            temp = temperature,
                            weather = weatherResId,
                            location = locationName,
                            month = monthResId,
                            dayOfWeek = dayResId,
                            day = day,
                        )

                        uiState = uiState.copy(weather = weather)
                    }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
    }

    private fun getWeatherResourceId(weatherCondition: String): Int {
        return when (weatherCondition) {
            "Thunderstorm" -> R.string.weather_thunderstorm
            "Drizzle" -> R.string.weather_drizzle
            "Rain" -> R.string.weather_rain
            "Snow" -> R.string.weather_snow
            "Mist" -> R.string.weather_mist
            "Haze" -> R.string.weather_haze
            "Smoke" -> R.string.weather_smoke
            "Dust" -> R.string.weather_dust
            "Fog" -> R.string.weather_fog
            "Sand" -> R.string.weather_sand
            "Ash" -> R.string.weather_ash
            "Squall" -> R.string.weather_squall
            "Tornado" -> R.string.weather_tornado
            "Clear" -> R.string.weather_clear
            "Clouds" -> R.string.weather_clouds
            else -> R.string.weather_default
        }
    }

    private fun getMonthResourceId(monthCondition: String): Int {
        return when (monthCondition) {
            "JANUARY" -> R.string.month_january
            "FEBRUARY" -> R.string.month_february
            "MARCH" -> R.string.month_march
            "APRIL" -> R.string.month_april
            "MAY" -> R.string.month_may
            "JUNE" -> R.string.month_june
            "JULY" -> R.string.month_july
            "AUGUST" -> R.string.month_august
            "SEPTEMBER" -> R.string.month_september
            "OCTOBER" -> R.string.month_october
            "NOVEMBER" -> R.string.month_november
            "DECEMBER" -> R.string.month_december
            else -> R.string.month_default
        }
    }

    private fun getDayResourceId(dayCondition: String): Int {
        return when (dayCondition) {
            "SUNDAY" -> R.string.day_sunday
            "MONDAY" -> R.string.day_monday
            "TUESDAY" -> R.string.day_tuesday
            "WEDNESDAY" -> R.string.day_wednesday
            "THURSDAY" -> R.string.day_thursday
            "FRIDAY" -> R.string.day_friday
            "SATURDAY" -> R.string.day_saturday
            else -> R.string.day_default
        }
    }
}