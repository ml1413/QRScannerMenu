package com.hutapp.org.qr_hut.utilities

import android.content.Context
import android.content.res.Configuration
import androidx.fragment.app.Fragment
import com.hutapp.org.qr_hut.R
import java.util.Locale

fun replaceFragment(parentFragment: Fragment, openFragment: Fragment) {
    val fragmentInContainer =
        parentFragment.parentFragmentManager.findFragmentById(R.id.container_main)
    if (fragmentInContainer == null || fragmentInContainer.javaClass != openFragment.javaClass)
        parentFragment.parentFragmentManager.beginTransaction()
            .replace(R.id.container_main, openFragment)
            .addToBackStack("")
            .commit()
}

