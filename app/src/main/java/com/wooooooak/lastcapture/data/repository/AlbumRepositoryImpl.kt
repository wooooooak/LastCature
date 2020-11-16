package com.wooooooak.lastcapture.data.repository

import com.wooooooak.lastcapture.data.model.AlbumLocal
import com.wooooooak.lastcapture.data.model.ImageLocal
import com.wooooooak.lastcapture.data.source.local.AlbumLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AlbumRepositoryImpl(
    private val albumLocalDataSource: AlbumLocalDataSource,
    private val dispatcher: CoroutineDispatcher
) :
    AlbumRepository {
    override suspend fun getAllAlbum(): List<AlbumLocal> = withContext(dispatcher) {
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

    override suspend fun removeSelectedAlbum(albumLocal: AlbumLocal) {
        albumLocalDataSource.removeSelectedAlbum(albumLocal)
    }

    override suspend fun getSelectedImage(
        count: Int
    ): List<ImageLocal> = withContext(dispatcher) {
        val albumNames = albumLocalDataSource.getSelectedAlbumList().map { it.name }
        albumLocalDataSource.getSelectedImageList(count, albumNames)
    }
}