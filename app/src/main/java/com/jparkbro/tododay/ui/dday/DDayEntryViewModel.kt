package com.jparkbro.tododay.ui.dday

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jparkbro.tododay.data.dday.DDayRepository
import com.jparkbro.tododay.model.DDay
import com.jparkbro.tododay.model.DDayEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

private const val TAG = "D_DAY_ENTRY_VIEW_MODEL"

@HiltViewModel
class DDayEntryViewModel @Inject constructor(
    private val dDayRepository: DDayRepository
) : ViewModel() {

    var uiState by mutableStateOf(DDayManageUiState())
        private set

    private fun validateInput(dday: DDay = uiState.dDay): Boolean {
        return with(dday) {
            title.isNotBlank()
        }
    }

    fun setState() {
        if (uiState.dDay.state) {

        } else {

        }
    }

    fun updateUiState(newState: DDayManageUiState) {
        uiState = DDayManageUiState(
            dDay = newState.dDay,
            state = newState.state,
            year = newState.year,
            month = newState.month,
            day = newState.day,
            dayOfWeek = setDateAndDayOfWeek(newState.year, newState.month, newState.day),
            isEntryValid = validateInput(newState.dDay)
        )
    }

    private fun setDateAndDayOfWeek(year: Int, month: Int, day: Int): String {
        val date = LocalDate.of(year, month, day)
        val formattedDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE)

        uiState = uiState.copy(
            dDay = uiState.dDay.copy(date = formattedDate)
        )

        val dayOfWeek = date.dayOfWeek
        return dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
    }

    suspend fun insertDDay() {
        if(validateInput()) {
            dDayRepository.insertDDay(uiState.dDay.toDDay())
        }
    }
}

fun DDay.toDDay(): DDayEntity = DDayEntity(
    id = id,
    title = title,
    date = date,
    state = state,
)