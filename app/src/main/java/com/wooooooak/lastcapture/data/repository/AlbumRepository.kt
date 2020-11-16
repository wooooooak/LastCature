package com.wooooooak.lastcapture.data.repository

import com.wooooooak.lastcapture.data.model.AlbumLocal
import com.wooooooak.lastcapture.data.model.ImageLocal

interface AlbumRepository {
    suspend fun getAllAlbum(): List<AlbumLocal>
    suspend fun addSelectedAlbum(albumLocal: AlbumLocal)
    suspend fun removeSelectedAlbum(albumLocal: AlbumLocal)
    suspend fun getSelectedImage(count: Int): List<ImageLocal>
}