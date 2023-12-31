package com.jparkbro.tododay.ui.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jparkbro.tododay.R
import com.jparkbro.tododay.data.weather.WeatherRepository
import com.jparkbro.tododay.model.LocationDetails
import com.jparkbro.tododay.model.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.roundToInt

private const val TAG = "WEATHER_VIEW_MODEL"

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    var uiState: WeatherUiState by mutableStateOf(WeatherUiState.Loading)
        private set

    fun getWeather(location: LocationDetails) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                uiState = WeatherUiState.Loading

                val localDate: LocalDate = LocalDate.now()

                val month = localDate.month.toString()
                val dayOfWeek = localDate.dayOfWeek.toString()
                val day = localDate.dayOfMonth.toString()

                weatherRepository.getWeather(lat = location.latitude, lon = location.longitude)
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
                        uiState = WeatherUiState.Success(weather)
                    }
            } catch (e: Exception) {
                uiState = WeatherUiState.Loading
//                uiState = WeatherUiState.Error TODO ERROR 대응 로직 추가
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