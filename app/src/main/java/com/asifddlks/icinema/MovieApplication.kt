package com.asifddlks.icinema

import android.app.Application
import android.content.Context

//
// Created by Asif Ahmed on 24/1/22.
//
class MovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MovieApplication.appContext = applicationContext
    }

    companion object {

        lateinit var appContext: Context

    }
}
