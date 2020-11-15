package com.wooooooak.lastcapture

import android.net.Uri
import com.wooooooak.lastcapture.data.model.AlbumLocal
import com.wooooooak.lastcapture.ui.model.AlbumModel

fun AlbumLocal.mapToUi(): AlbumModel {
    return AlbumModel(name = name, imageUri = Uri.parse(imageResource), isClicked = selected)
}

fun AlbumModel.mapToLocal(): AlbumLocal {
    return AlbumLocal(name = name, imageResource = imageUri.toString(), selected = isClicked)
}