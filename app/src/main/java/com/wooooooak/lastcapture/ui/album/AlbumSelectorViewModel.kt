package com.wooooooak.lastcapture.ui.album

import android.app.Application
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AlbumSelectorViewModel(application: Application) : AndroidViewModel(application) {

    init {
        viewModelScope.launch {
                Log.d("AlbumSelectorView", "launch")

            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val albumName = MediaStore.Images.Media.BUCKET_DISPLAY_NAME

            val projection = arrayOf(MediaStore.MediaColumns.DATA, albumName)


            val cursor = application.contentResolver.query(uri, projection, null, null, null)

            cursor?.let {
                Log.d(
                    "AlbumSelectorView",
                    it.getColumnIndex(MediaStore.Images.Media.BUCKET_ID).toString()
                )
                Log.d("AlbumSelectorView", it.count.toString())
                val totalImageList = generateSequence {
                    if (cursor.moveToNext()) {
                        Log.d("AlbumSelectorView", "hi")
                        cursor
                    } else null
                }
            }
        }

    }
}