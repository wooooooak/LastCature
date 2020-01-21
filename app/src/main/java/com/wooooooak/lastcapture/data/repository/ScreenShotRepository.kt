package com.wooooooak.lastcapture.data.repository

import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import androidx.core.database.getStringOrNull
import com.wooooooak.lastcapture.data.model.ScreenShot

class ScreenShotRepository {

    fun getScreenShot(application: Application, count: Int, folderNameSet: Set<String>): List<ScreenShot> {
        val INDEX_MEDIA_ID = MediaStore.Images.Media._ID
        val URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN
        )
        val cursor =
            application.contentResolver.query(URI, projection, null, null, MediaStore.Images.Media.DATE_TAKEN + " DESC")
        val imageUriList = mutableListOf<ScreenShot>()
        cursor?.let {
            while (it.moveToNext()) {
                val bucketDisplayNameColumnIndex = it.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
                val albumName = it.getStringOrNull(bucketDisplayNameColumnIndex)
                if (albumName in folderNameSet) {
                    val id = it.getLong(it.getColumnIndexOrThrow(INDEX_MEDIA_ID))
                    val name = it.getStringOrNull(it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)) ?: ""
                    val date = it.getStringOrNull(it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)) ?: ""
                    val contentUri = Uri.withAppendedPath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id.toString()
                    )
                    val screenShot = ScreenShot(contentUri, date, name)
                    imageUriList.add(screenShot)
                }
                if (imageUriList.size == count) return@let
            }
        }

        return imageUriList

    }

}