package com.wooooooak.lastcapture2.data.source.local

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import com.wooooooak.lastcapture2.data.dao.AlbumDao
import com.wooooooak.lastcapture2.data.model.AlbumLocal
import com.wooooooak.lastcapture2.data.model.ImageLocal
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

//private val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
private const val INDEX_MEDIA_ID = MediaStore.Images.Media._ID
private const val INDEX_ALBUM_NAME = MediaStore.Images.Media.BUCKET_DISPLAY_NAME
private const val INDEX_IMAGE_NAME = MediaStore.Images.Media.DISPLAY_NAME
private const val INDEX_DATE_TAKEN = MediaStore.Images.Media.DATE_TAKEN
private const val orderOption = "$INDEX_DATE_TAKEN DESC"

class AlbumLocalDataSource(
    private val contentResolver: ContentResolver,
    private val albumDao: AlbumDao?,
) : AlbumDataSource {
    override suspend fun getSelectedImageList(
        count: Int,
        albumNameList: List<String>,
    ): List<ImageLocal> = suspendCoroutine { continuation ->
        if (albumNameList.isEmpty()) {
            continuation.resume(listOf())
            return@suspendCoroutine
        }
        var imageList: List<ImageLocal> = listOf()
        val projection = arrayOf(
            INDEX_MEDIA_ID,
            INDEX_ALBUM_NAME,
            INDEX_IMAGE_NAME,
            INDEX_DATE_TAKEN
        )
        val where = albumNameList.joinToString(" OR ") { "$INDEX_ALBUM_NAME = '$it'" }
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            where,
            null,
            orderOption
        )
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(INDEX_MEDIA_ID)
            val imageNameColumn = it.getColumnIndexOrThrow(INDEX_IMAGE_NAME)
            imageList = generateSequence { if (it.moveToNext()) it else null }
                .map { cursor ->
                    val id = cursor.getLong(idColumn)
                    val imageName = cursor.getString(imageNameColumn)
                    val uri = Uri.withAppendedPath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id.toString()
                    )
                    ImageLocal(imageName, uri.toString())
                }
                .take(count)
                .toList()
        }
        continuation.resume(imageList)
    }

    override suspend fun getAllAlbum(): List<AlbumLocal> = suspendCoroutine { continuation ->
        var albumList: List<AlbumLocal> = listOf()
        val projection = arrayOf(
            INDEX_MEDIA_ID,
            INDEX_ALBUM_NAME,
            INDEX_DATE_TAKEN
        )
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            orderOption
        )
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(INDEX_MEDIA_ID)
            val albumNameColumn = it.getColumnIndexOrThrow(INDEX_ALBUM_NAME)

            albumList = generateSequence { if (it.moveToNext()) it else null }
                .map { cursor ->
                    val id = cursor.getLong(idColumn)
                    val albumName = cursor.getString(albumNameColumn)
                    val uri = Uri.withAppendedPath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id.toString()
                    )
                    AlbumLocal(albumName, uri.toString())
                }
                .groupBy { it.name }
                .map { it.value[0] }
        }

        continuation.resume(albumList)
    }

    override suspend fun addSelectedAlbum(album: AlbumLocal) = albumDao?.addSelectedAlbum(album)

    override suspend fun removeSelectedAlbum(album: AlbumLocal) = albumDao?.removeSelectedAlbum(album)

    override suspend fun getSelectedAlbumList(): List<AlbumLocal> {
        return albumDao?.getSelectedAlbumList() ?: listOf()
    }

}
