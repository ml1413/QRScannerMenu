package com.hutapp.org.qr_hut.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import com.hutapp.org.qr_hut.Activity.VisibilityAD
import com.hutapp.org.qr_hut.R
import com.hutapp.org.qr_hut.databinding.FragmentLastQRCodeBinding
import com.hutapp.org.qr_hut.utilities.MySharedPreferences


class FragmentLastQRCode : Fragment() {
    private lateinit var binding: FragmentLastQRCodeBinding
    private val sharedPreferences by lazy { MySharedPreferences(requireActivity().application) }
    private val mainActivity by lazy { requireActivity() as VisibilityAD }
    private var textUrl: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLastQRCodeBinding.inflate(inflater, container, false)
        initField()
        return binding.root
    }

    private fun initField() {
        val textFromShared = sharedPreferences.getSave(key = MySharedPreferences.KEY_STRING_URL)

        textUrl = if (textFromShared.contains("http")) {
            binding.fabLast.isInvisible = false
            textFromShared
        } else {
            getString(R.string.no_history)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.visibilityAD(true)
        setUrlInTextView()
            binding.fabLast.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(textUrl)))

        }
    }

    private fun setUrlInTextView() {
        binding.tvLastUrl.text = textUrl
    }

}