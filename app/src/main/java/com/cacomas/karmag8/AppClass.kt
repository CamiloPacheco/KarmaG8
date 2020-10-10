package com.cacomas.karmag8

import android.app.Application
import android.util.Log
import com.cacomas.karmag8.util.PreferenceProvider

class AppClass : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("VideoViewModel","AppClass onCreate")
        PreferenceProvider.initialize(this)
    }
}