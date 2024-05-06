package com.hutapp.org.qr_hut.fragment

import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.hutapp.org.qr_hut.Activity.VisibilityAD
import com.hutapp.org.qr_hut.R
import com.hutapp.org.qr_hut.utilities.SaveFileInPhone
import com.hutapp.org.qr_hut.databinding.FragmentShowQrCodeBinding


class FragmentShowQrCode : Fragment() {
    private lateinit var binding: FragmentShowQrCodeBinding
    private lateinit var name: String
    private val mainActivity by lazy { requireActivity() as VisibilityAD }
    private val saveFileInPhone by lazy { SaveFileInPhone(context = requireContext()) }
    private var bitmap: Bitmap? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowQrCodeBinding.inflate(layoutInflater, container, false)
        mainActivity.visibilityAD(true)
        getArgument()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bitmap?.let { binding.ivShowQr.setImageBitmap(it) }

        binding.btSave.setOnClickListener {
            requestPermissionForSaveFile()

            val dirPath =
                "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path}/qrCode/"
            Log.d("TAG1", "name:$name ")
            bitmap?.let { bitmap ->
                saveFileInPhone
                    .addDirectoryAndFile(dirPath = dirPath, fileName = name, bitmap = bitmap)
            }
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_PICK
        intent.type = "image/*"
        startActivity(intent)
    }

    private fun requestPermissionForSaveFile() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                READ_MEDIA_IMAGES,
                WRITE_EXTERNAL_STORAGE
            ), PERMISSION_REQUEST_IMAGE_STORAGE
        )
    }

    private fun getArgument() {
        name = requireArguments().getString(ARG_NAME, getString(R.string.no_name))

        bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getParcelable(ARG_BITMAP, Bitmap::class.java)
        } else {
            @Suppress("DEPRECATION")
            requireArguments().getParcelable(ARG_BITMAP)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String, bitmap: Bitmap) =
            FragmentShowQrCode().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                    putParcelable(ARG_BITMAP, bitmap)
                }
            }

        private const val ARG_NAME = "argument"
        private const val ARG_BITMAP = "bitmap"
        const val PERMISSION_REQUEST_IMAGE_STORAGE = 123123
    }
}