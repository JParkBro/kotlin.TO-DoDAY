package com.jparkbro.tododay

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TododayApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Application On Create")
    }
    companion object {
        const val TAG = "TODO_DAY_APPLICATION"
    }
}