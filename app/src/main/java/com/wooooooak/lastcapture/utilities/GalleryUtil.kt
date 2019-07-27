package com.wooooooak.lastcapture.utilities

import android.app.Application
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.wooooooak.lastcapture.data.Album
import com.wooooooak.lastcapture.data.Image
import java.io.File

class GalleryUtil {

    companion object {
        private val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        private val INDEX_MEDIA_URI = MediaStore.MediaColumns.DATA
        private val INDEX_ALBUM_NAME = MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        private val INDEX_IMAGE_NAME = MediaStore.Images.Media.DISPLAY_NAME
        private val INDEX_DATE_TAKEN = MediaStore.Images.Media.DATE_TAKEN
        private val orderOption = "$INDEX_DATE_TAKEN DESC"

        fun getAllAlbumList(application: Application): List<Album> {
            val projection = arrayOf(INDEX_MEDIA_URI, INDEX_ALBUM_NAME, INDEX_IMAGE_NAME)
            val cursor = application.contentResolver.query(uri, projection, null, null, orderOption)
            var albumList: List<Album> = listOf()
            if (cursor.moveToNext()) {
                val totalImageList = generateSequence { if (cursor.moveToNext()) cursor else null }
                    .map { getImage(it) }
                    .toList()

                albumList = totalImageList.asSequence()
                    .groupBy { image -> image.albumName }
                    .toSortedMap()
                    .map { entry -> getAlbum(entry) }
                    .toList()
            }
            return albumList
        }

        private fun getAlbum(entry: Map.Entry<String, List<Image>>): Album {

            val albumUriPath = entry.value[0].uri.path?.substringBeforeLast("/") ?: ""
            return Album(entry.key, albumUriPath, entry.value[0].uri, entry.value)
        }

        private fun getImage(cursor: Cursor): Image {
            return cursor.run {
                val name = getString(getColumnIndex(INDEX_IMAGE_NAME))
                val albumName = getString(getColumnIndex(INDEX_ALBUM_NAME))
                val mediaPath = getString(getColumnIndex(INDEX_MEDIA_URI))
                val mediaUri = Uri.fromFile(File(mediaPath))
                Image(name, albumName, mediaUri)
            }
        }
    }

}