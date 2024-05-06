package com.hutapp.org.qr_hut.utilities

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.hutapp.org.qr_hut.R

class MySharedPreferences(private val context: Application) {
    private val sharedPreferences: SharedPreferences
            by lazy { context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE) }

    fun save(string: String, key: String) {
        sharedPreferences.edit().putString(key, string).apply()
    }

    fun getSave(key: String): String {
        return sharedPreferences
            .getString(key, context.getString(R.string.empty))
            ?: context.getString(R.string.empty)
    }

    companion object {
        private const val SHARED_NAME = "my_prefs"
        const val KEY_STRING_URL = "string"
        const val KEY_LANGUAGE_LOCALE = "locale"
    }
}