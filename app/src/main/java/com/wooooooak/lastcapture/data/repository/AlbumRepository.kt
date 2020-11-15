package com.wooooooak.lastcapture.data.repository

import com.wooooooak.lastcapture.data.model.AlbumLocal

interface AlbumRepository {
    suspend fun getAllAlbum(): List<AlbumLocal>
    suspend fun addSelectedAlbum(albumLocal: AlbumLocal)
    suspend fun removeSelectedAlbum(albumLocal: AlbumLocal)
}