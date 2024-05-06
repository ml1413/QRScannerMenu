package com.hutapp.org.qr_hut.fragment

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.hutapp.org.qr_hut.Activity.VisibilityAD
import com.hutapp.org.qr_hut.R
import com.hutapp.org.qr_hut.contracts.SingleOutContract
import com.hutapp.org.qr_hut.databinding.FragmentScanFromGalleryBinding
import com.hutapp.org.qr_hut.utilities.GetStringFromImage
import com.hutapp.org.qr_hut.utilities.ShowStringInFragment
import com.hutapp.org.qr_hut.utilities.mySetTextColor

class FragmentScanFromGallery : Fragment() {
    private lateinit var binding: FragmentScanFromGalleryBinding
    private val getStringFromImage by lazy { GetStringFromImage(requireContext()) }
    private val showStringInFragment by lazy { ShowStringInFragment(parentFragmentManager) }
    private val mainActivity by lazy { requireActivity() as VisibilityAD }
    private val singleOutContract =
        registerForActivityResult(SingleOutContract()) { uri ->
            uri?.let {
                getStringFromImage.getSiringFromUri(
                    uri = it,
                    returnResult = { string ->
                        if (string.isNotEmpty()) showStringInFragment.openResultInFragment(url = string)
                    },
                    onFailureListener = {
                        val oldColor = binding.tvOpenImage.currentTextColor
                        binding.tvOpenImage.apply {
                            text =
                                this@FragmentScanFromGallery.getString(R.string.image_does_not_contain_qr_code)

                            mySetTextColor(android.R.color.holo_red_dark)
                            ObjectAnimator.ofFloat(this, View.ALPHA, 0.5f, 1f)
                                .apply {
                                    this.duration = 500
                                    this.repeatMode = ObjectAnimator.RESTART
                                    this.repeatCount = 5
                                    this.start()
                                    this.doOnEnd {
                                        text = resources.getString(R.string.open)
                                        setTextColor(oldColor)
                                    }
                                }
                        }
                    })
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScanFromGalleryBinding.inflate(inflater, container, false)
        mainActivity.visibilityAD(true)
        Log.d("TAG1", "onCreateView: $this")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabLast.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            singleOutContract.launch(intent)
        }
    }


}