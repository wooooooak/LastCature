package com.wooooooak.lastcapture.fake

import androidx.annotation.VisibleForTesting
import com.wooooooak.lastcapture.data.model.AlbumLocal
import com.wooooooak.lastcapture.data.repository.AlbumRepository

class FakeAlbumRepository : AlbumRepository {

    private var albumListData: List<AlbumLocal> = listOf()

    override suspend fun getAllAlbum(): List<AlbumLocal> {
        return albumListData
    }

    override suspend fun addSelectedAlbum(albumLocal: AlbumLocal) {
        albumListData + albumLocal
    }

    override suspend fun removeSelectedAlbum(albumLocal: AlbumLocal) {
        albumListData - albumLocal
    }

    @VisibleForTesting
    fun addAlbumList(albumList: List<AlbumLocal>) {
        albumListData += albumList
    }
}