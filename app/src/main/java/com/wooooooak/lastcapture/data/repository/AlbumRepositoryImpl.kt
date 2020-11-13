package com.wooooooak.lastcapture.data.repository

import com.wooooooak.lastcapture.data.model.AlbumLocal
import com.wooooooak.lastcapture.data.source.local.AlbumLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumRepositoryImpl(private val albumLocalDataSource: AlbumLocalDataSource) : AlbumRepository {
    override suspend fun getAllAlbum(): List<AlbumLocal> = withContext(Dispatchers.IO) {
        // 모든 앨범에서 내가 선택한 앨범을 표기해서 주기
        val allAlbumList = albumLocalDataSource.getAllAlbum()
        val selectedAlbumList = albumLocalDataSource.getSelectedAlbumList()
        allAlbumList.map { albumLocal ->
            selectedAlbumList.findLast { it.name == albumLocal.name } ?: albumLocal
        }
    }

    override suspend fun addSelectedAlbum(albumLocal: AlbumLocal) {
        albumLocalDataSource.addSelectedAlbum(albumLocal)
    }

    override fun removeSelectedAlbum(albumLocal: AlbumLocal) {
    }
}