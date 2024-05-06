package com.hutapp.org.qr_hut.Activity

import android.Manifest.permission.CAMERA
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.hutapp.org.qr_hut.R
import com.hutapp.org.qr_hut.databinding.ActivityMainBinding
import com.hutapp.org.qr_hut.fragment.FragmentAddQrCode
import com.hutapp.org.qr_hut.fragment.FragmentLastQRCode
import com.hutapp.org.qr_hut.fragment.FragmentQRScanner
import com.hutapp.org.qr_hut.fragment.FragmentScanFromGallery
import com.hutapp.org.qr_hut.fragment.FragmentSetting
import com.hutapp.org.qr_hut.utilities.MySharedPreferences
import com.hutapp.org.qr_hut.utilities.setLanguage

interface VisibilityAD {
    fun visibilityAD(boolean: Boolean)

}

class MainActivity : AppCompatActivity(), VisibilityAD {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val sharedPreferences by lazy { MySharedPreferences(application) }


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences.getSave(key = MySharedPreferences.KEY_LANGUAGE_LOCALE).apply {
            if (this != getString(R.string.empty)) {
                setLanguage(localeCountry = this@apply, this@MainActivity)
            }
        }


        setContentView(binding.root)
        checkSelfPermission()
        clickOnBottomNav()
        clickOnNavigationView()

    }


    private fun clickOnNavigationView() {
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_nav_scan -> {
                    /** убирает очистить из  бек стека все что там было*/
                    clearFragmentBackStack()
                }

                R.id.item_nav_history -> {
                    setFragmentInContainerMain(FragmentLastQRCode(), addToBackStack = true)
                }

                R.id.item_nav_add_qr_code -> {
                    setFragmentInContainerMain(FragmentAddQrCode(), addToBackStack = true)
                }

                R.id.item_nav_scan_image -> {
                    setFragmentInContainerMain(FragmentScanFromGallery(), addToBackStack = true)
                }

                R.id.item_nav_setting -> {
                    setFragmentInContainerMain(FragmentSetting(), addToBackStack = true)
                }
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun clickOnBottomNav() {
        binding.fabMain.setOnClickListener {
            val boolean = binding.drawerLayout.isDrawerOpen(GravityCompat.START)

            if (boolean) binding.drawerLayout.closeDrawer(GravityCompat.START)
            else binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    fun checkSelfPermission() {

        //TODO спрашивать ппермишен у разных версий
        val selfCamera = ContextCompat.checkSelfPermission(this, CAMERA)
        val granted = PERMISSION_GRANTED

        if (selfCamera == granted) {
            setFragmentInContainerMain(FragmentQRScanner())
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(CAMERA), PERMISSION_REQUEST_CAMERA)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            //__________________________________________________________________________________________
            PERMISSION_REQUEST_CAMERA -> {
                if (permissions[0] == CAMERA && grantResults[0] == PERMISSION_GRANTED) {
                    setFragmentInContainerMain(FragmentQRScanner())
                } else {
                    myToast(string = getString(R.string.need_permission_camera))
                }
            }
            //______________________________________________________________________________________
        }
    }

    private fun myToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }


    private fun setFragmentInContainerMain(fragment: Fragment, addToBackStack: Boolean = false) {

        val fragmentInContainer = supportFragmentManager.findFragmentById(R.id.container_main)
        if (fragmentInContainer == null || fragmentInContainer.javaClass != fragment.javaClass) {

            if (fragmentInContainer !is FragmentQRScanner) clearFragmentBackStack()

            with(supportFragmentManager.beginTransaction()) {
                if (addToBackStack) addToBackStack("")
                replace(R.id.container_main, fragment)
                commit()
            }
        }
    }

    private fun clearFragmentBackStack() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object {
        const val PERMISSION_REQUEST_CAMERA = 123321
    }

    override fun visibilityAD(boolean: Boolean) {
        //todo отключил рекламу по всему проекту
    }
}