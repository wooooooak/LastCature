package com.wooooooak.lastcapture

import com.wooooooak.lastcapture.data.model.AlbumLocal
import com.wooooooak.lastcapture.ui.model.AlbumModel

fun AlbumLocal.mapToUi(): AlbumModel {
    return AlbumModel(name = name, imageUri = uri)
}