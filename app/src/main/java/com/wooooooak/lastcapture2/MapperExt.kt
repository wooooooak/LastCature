package com.wooooooak.lastcapture2

import com.wooooooak.lastcapture2.data.model.AlbumLocal
import com.wooooooak.lastcapture2.data.model.ImageLocal
import com.wooooooak.lastcapture2.ui.model.AlbumModel
import com.wooooooak.lastcapture2.ui.model.ImageModel

fun AlbumLocal.mapToUi(): AlbumModel {
    return AlbumModel(name = name, image = imageResource, isClicked = selected)
}

fun AlbumModel.mapToLocal(): AlbumLocal {
    return AlbumLocal(name = name, imageResource = image, selected = isClicked)
}

fun ImageLocal.mapToUi(): ImageModel {
    return ImageModel(name = name, imagePath = imagePath)
}