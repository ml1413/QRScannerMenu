package com.hutapp.org.qr_hut.utilities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class GetStringFromImage(private val context: Context) {
    fun getSiringFromUri(
        uri: Uri,
        returnResult: (String) -> Unit = {},
        onFailureListener: () -> Unit
    ) {

        val bitmap = getBitmapFromUri(uri = uri)
        val resizeBitmap = getResizeBitmap(bitmap, 1000)
        val image = InputImage.fromBitmap(resizeBitmap, 0)

        val options = BarcodeScannerOptions.Builder().enableAllPotentialBarcodes().build()
        val scanner = BarcodeScanning.getClient(options)

        scanner.process(image).addOnSuccessListener { barcodes ->
            try {
                val code = barcodes[0].rawValue
                code?.let { returnResult(it) }
            } catch (e: Exception) {
                onFailureListener()
                Log.d("TAG1", "getSiringFromUri:${e.cause} ${e.message}")
            }
        }.addOnFailureListener {
            onFailureListener()
        }
    }

    private fun toast(string: String) {
        Toast.makeText(context.applicationContext, string, Toast.LENGTH_SHORT).show()
    }

    private fun getResizeBitmap(bitmap: Bitmap, maxSize: Int): Bitmap {
        val isVertical = bitmap.width < bitmap.height
        val max = Math.max(bitmap.width, bitmap.height).toDouble()
        val min = Math.min(bitmap.width, bitmap.height).toDouble()

        return if (max <= maxSize) {
            bitmap
        } else {
            val percent = maxSize * 100 / max
            val maxResize = max * percent / 100
            val minResize = min * percent / 100
            val wResize = (if (isVertical) minResize else maxResize).toInt()
            val hResize = (if (isVertical) maxResize else minResize).toInt()

            Bitmap.createScaledBitmap(bitmap, wResize, hResize, false)
        }

    }


    private fun getBitmapFromUri(uri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder
                .createSource(context.contentResolver, uri)
                .let { ImageDecoder.decodeBitmap(it) }
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
    }

}