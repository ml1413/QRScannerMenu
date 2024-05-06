package com.hutapp.org.qr_hut.utilities

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class GenerateQRCode(private val context: Context) {
    fun generateQRCode(width: Int, height: Int, text: String): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val multiFormatWriter = MultiFormatWriter()
            val bitMatrix = multiFormatWriter.encode(
                text,
                BarcodeFormat.QR_CODE,
                width, height
            )
            val barcodeEncoder = BarcodeEncoder()
            bitmap = barcodeEncoder.createBitmap(bitMatrix)
        } catch (e: WriterException) {
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("TAG1", "WriterException: ${e.message}")
        } catch (e: IllegalArgumentException) {
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("TAG1", "IllegalArgumentException: ${e.message}")

        }
        return bitmap
    }
}