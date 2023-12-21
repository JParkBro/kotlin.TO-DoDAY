package com.jparkbro.tododay.ui.dday

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class DDayViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = mutableStateOf(DDayUiState())
    val uiState: DDayUiState by _uiState

    init {
        getDDays()
    }

    private fun getDDays() {

    }
}