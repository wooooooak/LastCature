package com.wooooooak.lastcapture.ui.model

import android.net.Uri

data class AlbumModel(
    val name: String,
    val imageUri: Uri,
    val isClicked: Boolean = false,
)
