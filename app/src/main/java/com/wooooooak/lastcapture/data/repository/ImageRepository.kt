package com.wooooooak.lastcapture.data.repository

import com.wooooooak.lastcapture.data.model.ImageLocal

interface ImageRepository {
    fun getImageInSelectedAlbum(): List<ImageLocal>
}