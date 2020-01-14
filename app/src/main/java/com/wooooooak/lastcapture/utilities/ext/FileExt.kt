package com.wooooooak.lastcapture.utilities.ext

import java.io.File
import java.text.SimpleDateFormat
import java.util.*

val File.isImageFile: Boolean
    get() = when (extension) {
        "jpeg", "png", "jpg" -> true
        else -> false
    }

val File.lastModifiedTime: String
    get() = SimpleDateFormat("yyyy년MM월dd일").format(Date(lastModified()))

fun File.getRecentImageList(count: Int) = File(path).listFiles()
    ?.sortedBy { it.lastModified() }
    ?.reversed()
    ?.asSequence()
    ?.filter { isImageFile }
    ?.take(count)
    ?.toList() ?: listOf()