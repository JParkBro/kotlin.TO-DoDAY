package com.jparkbro.tododay.ui.weather

import com.jparkbro.tododay.model.Weather

sealed interface WeatherUiState {
    object Loading : WeatherUiState
    data class Success(val weather: Weather) : WeatherUiState
//    object Error : WeatherUiState // TODO ERROR 대응 로직 추가
}
