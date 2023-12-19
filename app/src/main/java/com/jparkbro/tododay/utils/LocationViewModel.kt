package com.jparkbro.tododay.utils

import android.Manifest
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

private const val TAG = "LOCATION_VIEW_MODEL"

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationLiveData: LocationLiveData
) : ViewModel() {
    fun getLocationData() = locationLiveData

    private var _permissionState = MutableStateFlow(false)
    val permissionState: StateFlow<Boolean> = _permissionState.asStateFlow()

    fun updatePermissionStatus(permission: Boolean) {
        _permissionState.value =  permission
    }
}