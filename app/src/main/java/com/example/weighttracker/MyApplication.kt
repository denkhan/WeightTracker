package com.example.weighttracker

import android.app.Application
import android.util.Log
import com.example.weighttracker.di.weightAppModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.android.startKoin


class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin(this, listOf(weightAppModule))
        Log.d("BAJS3", "KOIN STARTED")
        AndroidThreeTen.init(this)
    }
}