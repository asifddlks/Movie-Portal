package com.asifddlks.icinema

import android.content.Context
import android.content.SharedPreferences

//
// Created by Asif Ahmed on 20/1/22.
//
class AppPrefs(context: Context) {
    private lateinit var appSharedPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor

    fun AppPrefs(context: Context) {
        appSharedPrefs = context.getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
        prefsEditor = appSharedPrefs.edit()
    }

    fun getIsFirstLaunch(): Boolean {
        return appSharedPrefs!!.getBoolean("isFirstLaunch", true)
    }

    fun setIsFirstLaunch(isFirstLaunch: Boolean) {
        prefsEditor!!.putBoolean("isFirstLaunch", isFirstLaunch).commit()
    }

    fun getAccessToken(): String? {
        return appSharedPrefs!!.getString("accessToken", "")
    }

    fun setAccessToken(accessToken: String?) {
        prefsEditor!!.putString("accessToken", accessToken).commit()
    }

    fun getLanguage(): String? {
        return appSharedPrefs!!.getString("Language", "en")
    }

    fun setLanguage(language: String?) {
        prefsEditor!!.putString("Language", language).apply()
    }
}