package com.wooooooak.lastcapture.data

import android.net.Uri

data class Album(
    val name: String,
    val albumUriPath: String,
    val thumbnail: Uri,
    val images: List<Image>
) {
    var isSelected: Boolean = false
}