package com.hutapp.org.qr_hut.utilities

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.util.Log
import android.widget.Toast
import com.hutapp.org.qr_hut.R
import java.io.File
import java.io.FileOutputStream

class SaveFileInPhone(private val context: Context) {
    private lateinit var imageFile: File
    fun addDirectoryAndFile(dirPath: String, fileName: String, bitmap: Bitmap) {
        createDirectoryAdnFile(dirPath, fileName)
        saveFileInDevice(bitmap = bitmap, imageFile = imageFile)
        updateGallery()
    }

    private fun createDirectoryAdnFile(dirPath: String, fileName: String) {
        val directory = File(dirPath)
        if (!directory.exists()) directory.mkdirs()
        imageFile = File(directory, "$fileName.PNG")
    }

    private fun saveFileInDevice(bitmap: Bitmap, imageFile: File) {
        try {
            val fos = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
            Toast.makeText(context, context.getString(R.string.save_done), Toast.LENGTH_SHORT)
                .show()
        } catch (e: Exception) {
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateGallery() {
        val saveImagePath = imageFile.absolutePath
        MediaScannerConnection.scanFile(context, arrayOf(saveImagePath), null, null)
    }

}