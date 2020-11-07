package com.wooooooak.lastcapture.mapper

import com.wooooooak.lastcapture.data.model.AlbumLocal
import com.wooooooak.lastcapture.ui.model.AlbumModel

class AlbumMapper : Mapper<AlbumLocal, AlbumModel> {
    override fun map(to: AlbumLocal): AlbumModel {
        return AlbumModel(name = to.name, imageUrl = to.uri)
    }
}