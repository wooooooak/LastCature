package com.wooooooak.lastcapture.data.model

import android.net.Uri

data class Album(
    val name: String,
    val albumUriPath: String,
    val thumbnailUri: Uri,
    val images: List<Image>
) {
    var isSelected: Boolean = false
}