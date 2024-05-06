package com.hutapp.org.qr_hut.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.hutapp.org.qr_hut.R
import com.hutapp.org.qr_hut.utilities.MySharedPreferences
import com.hutapp.org.qr_hut.utilities.setLanguage

class SplashActivity : AppCompatActivity() {
    private val sharedPreferences by lazy { MySharedPreferences(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            sharedPreferences.getSave(key = MySharedPreferences.KEY_LANGUAGE_LOCALE).apply {
                if (this != getString(R.string.empty)) {
                    setLanguage(localeCountry = this@apply, this@SplashActivity)
                }
            }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000)
    }
}