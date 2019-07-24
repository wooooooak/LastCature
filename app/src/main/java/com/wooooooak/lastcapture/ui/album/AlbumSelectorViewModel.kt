package com.wooooooak.lastcapture.ui.album

import android.app.Application
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.wooooooak.lastcapture.data.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class AlbumSelectorViewModel(application: Application) : AndroidViewModel(application) {

    val TAG = "AlbumSelectorView"
    val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    private val INDEX_MEDIA_URI = MediaStore.MediaColumns.DATA
    private val INDEX_ALBUM_NAME = MediaStore.Images.Media.BUCKET_DISPLAY_NAME
    private val INDEX_IMAGE_NAME = MediaStore.Images.Media.DISPLAY_NAME
    private val INDEX_DATE_TAKEN = MediaStore.Images.Media.DATE_TAKEN
    private val orderOption = "$INDEX_DATE_TAKEN DESC"

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val projection = arrayOf(INDEX_MEDIA_URI, INDEX_ALBUM_NAME, INDEX_IMAGE_NAME)
            val cursor = application.contentResolver.query(uri, projection, null, null, orderOption)

            if (cursor.moveToNext()) {
                val totalImageList = generateSequence { if (cursor.moveToNext()) cursor else null }
                    .map {
                        getImage(it)
                    }.toList()

                var albumList = totalImageList.asSequence()
                    .groupBy { image -> image.albumName }

                albumList.forEach {
                    Log.d(TAG, it.toString())
                }
            }
        }
    }

    private fun getImage(cursor: Cursor): Image {
        return cursor.run {
            val name = getString(getColumnIndex(INDEX_IMAGE_NAME))
            val albumName = getString(getColumnIndex(INDEX_ALBUM_NAME))
            val mediaPath = getString(getColumnIndex(INDEX_MEDIA_URI))
            val mediaUri: Uri = Uri.fromFile(File(mediaPath))
            Image(name, albumName, mediaUri)
        }
    }
}