package com.wooooooak.lastcapture

import androidx.core.net.toUri
import com.wooooooak.lastcapture.data.model.AlbumLocal
import com.wooooooak.lastcapture.ui.model.AlbumModel

fun AlbumLocal.mapToUi(): AlbumModel {
    return AlbumModel(name = name, imageUri = imageResource.toUri(), isClicked = selected)
}

fun AlbumModel.mapToLocal(): AlbumLocal {
    return AlbumLocal(name = name, imageResource = imageUri.toString(), selected = isClicked)
}