package com.chetu.kotlinchat

import android.app.Application
import android.content.Context

class AppController : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        lateinit var appContext: AppController
    }
}