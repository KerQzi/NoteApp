package com.example.noteapp.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {

    private lateinit var sharedPref: SharedPreferences

    fun unit(context: Context) {
        sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
    }

    var isFirstTimeOnBoard: Boolean
        get() = sharedPref.getBoolean("isFirstTimeOnBoard", true)
        set(value) = sharedPref.edit().putBoolean("isFirstTimeOnBoard", value).apply()

    var isFirstSignIn: Boolean
        get() = sharedPref.getBoolean("isSignIn", true)
        set(value) = sharedPref.edit().putBoolean("isSignIn", value).apply()

    var isGridLayout: Boolean
        get() = sharedPref.getBoolean("isGridLayout", false)
        set(value) = sharedPref.edit().putBoolean("isGridLayout", value).apply()

}