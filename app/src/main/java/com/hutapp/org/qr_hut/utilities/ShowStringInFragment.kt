package com.hutapp.org.qr_hut.utilities

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hutapp.org.qr_hut.R
import com.hutapp.org.qr_hut.fragment.FragmentBarcode
import com.hutapp.org.qr_hut.fragment.FragmentShowText
import com.hutapp.org.qr_hut.fragment.FragmentWebVieW

const val TAG = "ShowStringInFragment"

class ShowStringInFragment(private val parentFragmentManager: FragmentManager) {
    fun openResultInFragment(url: String, stringFromSaveInShared: (String) -> Unit = {}) {
        val webAddress = url.startsWith("https://") || url.startsWith("http://")
        Log.d(TAG, "openResultInFragment: $url ")

        if (webAddress) {
            /** веб адреса */
            stringFromSaveInShared(url)
            val fragment = FragmentWebVieW.newInstance(string = url)
            setFragmentInContainerMain(fragment = fragment)
        }

        if (!webAddress && url.length < 30) {
            /** штрихкод  */
            val fragment = FragmentBarcode.newInstance(string = url)
            setFragmentInContainerMain(fragment = fragment)
        }

        if (!webAddress && url.length > 30) {
            /**показать фрагмент с длинным текстом*/
            val fragment = FragmentShowText.newInstance(string = url)
            setFragmentInContainerMain(fragment = fragment)
        }
//        if (webAddress) {
//            if (url.startsWith("https://play.google")) {
//                App.getInstance().applicationContext.startActivity(
//                    Intent(
//                        Intent.ACTION_VIEW,
//                        Uri.parse(url)
//                    )
//                )
//            }
//        }
    }

    private fun setFragmentInContainerMain(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container_main, fragment)
            .addToBackStack("")
            .commit()
    }
}