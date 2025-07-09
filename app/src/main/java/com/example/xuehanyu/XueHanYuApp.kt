package com.example.xuehanyu

import android.app.Application
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