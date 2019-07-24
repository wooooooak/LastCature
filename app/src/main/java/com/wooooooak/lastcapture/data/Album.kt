package com.wooooooak.lastcapture.data

import android.net.Uri

data class Album(val name: String, val thumbnail: Uri, val images: List<Image>) {
    val imageCount: Int = images.size
    var isSelected: Boolean = false
}