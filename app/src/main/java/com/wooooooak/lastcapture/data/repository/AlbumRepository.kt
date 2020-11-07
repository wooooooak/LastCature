package com.wooooooak.lastcapture.data.repository

import com.wooooooak.lastcapture.data.model.AlbumLocal

interface AlbumRepository {
    fun getAllAlbum(): List<AlbumLocal>
    fun addSelectedAlbum(albumLocal: AlbumLocal)
    fun removeSelectedAlbum(albumLocal: AlbumLocal)
}