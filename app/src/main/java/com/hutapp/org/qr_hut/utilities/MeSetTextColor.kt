package com.hutapp.org.qr_hut.utilities

import android.widget.TextView
import androidx.core.content.ContextCompat

fun TextView.mySetTextColor(color: Int) {
    this.setTextColor(ContextCompat.getColor(this.context, color))
}