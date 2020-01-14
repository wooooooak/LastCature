package com.wooooooak.lastcapture.utilities

import android.app.Application
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.orhanobut.logger.Logger
import com.wooooooak.lastcapture.data.model.Album
import com.wooooooak.lastcapture.data.model.Image

private val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
private const val INDEX_MEDIA_ID = MediaStore.Images.Media._ID
private const val INDEX_ALBUM_NAME = MediaStore.Images.Media.BUCKET_DISPLAY_NAME
private const val INDEX_IMAGE_NAME = MediaStore.Images.Media.DISPLAY_NAME
private const val INDEX_DATE_TAKEN = MediaStore.Images.Media.DATE_TAKEN
private const val orderOption = "$INDEX_DATE_TAKEN DESC"

fun getAllAlbumList(application: Application): List<Album> {
    val projection = arrayOf(INDEX_MEDIA_ID, INDEX_ALBUM_NAME, INDEX_IMAGE_NAME)
    val cursor = application.contentResolver.query(uri, projection, null, null, orderOption)
    var albumList: List<Album> = listOf()
    cursor?.use {
        if (cursor.moveToNext()) {
            val totalImageList =
                generateSequence { if (cursor.moveToNext()) cursor else null }
                    .map { getImage(it) }
                    .toList()

            albumList = totalImageList.asSequence()
                .groupBy { image -> image.albumName }
                .toSortedMap()
                .map { entry -> getAlbum(entry) }
                .toList()
        }
    }
    return albumList
}

// entry.key = album 이름
// entry.value = album안에 image리스트
private fun getAlbum(entry: Map.Entry<String, List<Image>>): Album {
    val albumUriPath = entry.value[0].uri.toString()
    Logger.d(entry)
    return Album(
        name = entry.key,
        albumUriPath = albumUriPath,
        thumbnailUri = entry.value[0].uri,
        images = entry.value
    )
}

private fun getImage(cursor: Cursor) =
    cursor.run {
        val id = getLong(getColumnIndexOrThrow(INDEX_MEDIA_ID))
        val name = getString(getColumnIndex(INDEX_IMAGE_NAME))
        val albumName = getString(getColumnIndex(INDEX_ALBUM_NAME))
//        val mediaPath = getString(getColumnIndex(INDEX_MEDIA_ID))
//        val mediaUri = Uri.fromFile(File(mediaPath))

        val contentUri = Uri.withAppendedPath(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            id.toString()
        )
        Image(name, albumName, contentUri)
    }
