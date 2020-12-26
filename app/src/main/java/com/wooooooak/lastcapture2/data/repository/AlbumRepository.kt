package com.wooooooak.lastcapture2.data.repository

import com.wooooooak.lastcapture2.data.model.AlbumLocal
import com.wooooooak.lastcapture2.data.model.ImageLocal

interface AlbumRepository {
    suspend fun getAllAlbum(): List<AlbumLocal>
    suspend fun addSelectedAlbum(albumLocal: AlbumLocal)
    suspend fun removeSelectedAlbum(albumLocal: AlbumLocal)
    suspend fun getSelectedImage(count: Int): List<ImageLocal>
}