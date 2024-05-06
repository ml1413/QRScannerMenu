package com.hutapp.org.qr_hut.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hutapp.org.qr_hut.Activity.VisibilityAD
import com.hutapp.org.qr_hut.R
import com.hutapp.org.qr_hut.databinding.FragmentQRScannerBinding
import com.hutapp.org.qr_hut.utilities.MySharedPreferences
import com.hutapp.org.qr_hut.utilities.ShowStringInFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

private const val ON_RESUME = "on resume"
private const val ON_PAUSE = "on pause"

class FragmentQRScanner : Fragment(), ZBarScannerView.ResultHandler {
    private lateinit var binding: FragmentQRScannerBinding
    private val sharedPreferences by lazy { MySharedPreferences(requireActivity().application) }
    private val showStringInFragment by lazy { ShowStringInFragment(parentFragmentManager) }
    private val mainActivity by lazy { requireActivity() as VisibilityAD }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQRScannerBinding.inflate(inflater, container, false)
        Log.d("TAG1", "onCreateView: $this")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.visibilityAD(false)
        clickOnImageViewFlash()

    }

    override fun onResume() {
        startStopCamera(ON_RESUME)
        super.onResume()
    }

    override fun onPause() {
        Log.d("TAG1", "onPause: ")
        startStopCamera(ON_PAUSE)
        super.onPause()
    }

    override fun onDestroy() {
        Log.d("TAG1", "onDestroy: ")
        super.onDestroy()
    }


    private fun startStopCamera(constanta: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            when (constanta) {
                ON_RESUME -> startRestartCamera()
                ON_PAUSE -> stopCamera()
            }
        }
    }

    private suspend fun stopCamera() {
        binding.zBar.stopCamera()
    }

    private suspend fun startRestartCamera() {
        delay(300)
        binding.zBar.setResultHandler(this@FragmentQRScanner)
        binding.zBar.startCamera()
    }

    private fun clickOnImageViewFlash() {
        binding.ivLight.setOnClickListener {
            onOffFlash()
            if (binding.zBar.flash) {
                binding.ivLight.setColorFilter(
                    ContextCompat.getColor(requireContext(), android.R.color.holo_orange_light)
                )
            } else {
                binding.ivLight.colorFilter = null
            }
        }
    }


    private fun onOffFlash() {
        binding.zBar.flash = !binding.zBar.flash
    }


    override fun handleResult(result: Result?) {
        val url = result?.contents ?: getString(R.string.empty)

        showStringInFragment.openResultInFragment(url) { webUrl ->
            saveUrlInSharedPreferences(webUrl)
        }
    }

    private fun saveUrlInSharedPreferences(url: String) {
        sharedPreferences.save(string = url, key = MySharedPreferences.KEY_STRING_URL)
    }

}