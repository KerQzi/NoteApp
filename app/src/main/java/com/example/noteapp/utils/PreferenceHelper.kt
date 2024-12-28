package com.example.noteapp.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {

    private lateinit var sharedPref: SharedPreferences

    fun unit(context: Context) {
        sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
    }

    var isFirstTimeOnBoard: Boolean
        get() = sharedPref.getBoolean("showed", false)
        set(value) = sharedPref.edit().putBoolean("showed", value).apply()

    var isSignIn: Boolean
        get() = sharedPref.getBoolean("showed", false)
        set(value) = sharedPref.edit().putBoolean("showed", value).apply()

    var isGridLayout: Boolean
        get() = sharedPref.getBoolean("showed", false)
        set(value) = sharedPref.edit().putBoolean("showed", value).apply()

}