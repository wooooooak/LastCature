package com.wooooooak.lastcapture.data.model

import android.net.Uri

data class AlbumLocal(
    val name: String,
    val uri: Uri,
    val selected: Boolean = false
)