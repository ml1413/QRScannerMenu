package com.hutapp.org.qr_hut.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.hutapp.org.qr_hut.Activity.VisibilityAD
import com.hutapp.org.qr_hut.R
import com.hutapp.org.qr_hut.databinding.FragmentWebWievBinding

const val TAG = "FragmentWebVieW"

class FragmentWebVieW : Fragment() {
    private lateinit var binding: FragmentWebWievBinding
    private var url: String = ""
    private val mainActivity by lazy { requireActivity() as VisibilityAD }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //todo log fragment
        Log.d("TAG1", "onCreateView:FragmentWebVieW $this ")
        binding = FragmentWebWievBinding.inflate(inflater, container, false)
        getArgument()
        setClientInWebView()
        mainActivity.visibilityAD(true)
        return binding.root
    }

    private fun setClientInWebView() {
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.progressBar.isVisible = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progressBar.isVisible = false
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return try {
                    if (url?.startsWith("intent") == true) {
                        val subString = url.substring(url.indexOf("https://"))
                        view?.context?.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(subString)
                            )
                        )
                        true
                    } else if (url?.startsWith("market:") == true) {
                        binding.webView.isVisible = false
                        view?.context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        true
                    } else {
                        false
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), R.string.error_reader, Toast.LENGTH_SHORT)
                        .show()
                    Log.e(TAG, "shouldOverrideUrlLoading: Exception", e)
                    false
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        url.let { binding.webView.loadUrl(it) }
    }

    private fun getArgument() {
        url = requireArguments().getString(ARG_PARAM_STRING, "Argument empty")
    }

    companion object {

        fun newInstance(string: String) =
            FragmentWebVieW().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_STRING, string)
                }
            }

        const val ARG_PARAM_STRING = "STRING"
    }
}