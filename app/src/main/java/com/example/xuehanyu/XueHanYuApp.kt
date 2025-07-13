package com.example.xuehanyu

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class XueHanYuApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any global resources or configurations here if needed
        FirebaseApp.initializeApp(this)
    }
}