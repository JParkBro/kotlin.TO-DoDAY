package com.jparkbro.tododay.utils

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel

private const val TAG = "LOCATION_VIEW_MODEL"

class LocationViewModel (application: Application) : AndroidViewModel(application) {
    private val locationLiveData = LocationLiveData(application)

    fun getLocationData() = locationLiveData

}