package com.wooooooak.lastcapture.data.repository

import com.wooooooak.lastcapture.data.model.AlbumLocal

class IAlbumRepository : AlbumRepository {
    override fun getAllAlbum(): List<AlbumLocal> {
        // 모든 앨범에서 내가 선택한 앨범을 표기해서 주기
        return listOf(
            AlbumLocal("labum1", "", true),
            AlbumLocal("labum2", ""),
            AlbumLocal("labum3", ""),
            AlbumLocal("labum4", "", true),
            AlbumLocal("labum5", ""),
            AlbumLocal("labum6", ""),
            AlbumLocal("labum7", "", true),
            AlbumLocal("labum8", ""),
            AlbumLocal("labum9", ""),
            AlbumLocal("labum9", ""),
            AlbumLocal("labum9", ""),
            AlbumLocal("labum9", ""),
            AlbumLocal("labum9", ""),
            AlbumLocal("labum9", ""),
            AlbumLocal("labum9", ""),
            AlbumLocal("labum9", ""),
            AlbumLocal("labum9", ""),
            AlbumLocal("labum9", ""),
            AlbumLocal("labum9", ""),
            AlbumLocal("labum9", ""),
            AlbumLocal("labum9", ""),
            AlbumLocal("labum9", ""),
        )
    }

    override fun addSelectedAlbum(albumLocal: AlbumLocal) {
    }

    override fun removeSelectedAlbum(albumLocal: AlbumLocal) {
    }
}