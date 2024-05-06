package com.hutapp.org.qr_hut.utilities

import android.content.Context
import java.util.Locale

fun setLanguage(localeCountry: String, context: Context) {
    val locale = Locale(localeCountry)
    Locale.setDefault(locale)
    val config = context.resources.configuration
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}