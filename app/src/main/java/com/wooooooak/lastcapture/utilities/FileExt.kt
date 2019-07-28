package com.wooooooak.lastcapture.utilities

import java.io.File

fun File.getRecentImageList(count: Int): List<File> {

    fun isImage(file: File): Boolean {
        return when (file.extension) {
            "jpeg", "png", "jpg" -> true
            else -> false
        }
    }

    return File(path).listFiles()
        ?.sortedBy { it.lastModified() }
        ?.reversed()
        ?.asSequence()
        ?.filter { isImage(it) }
        ?.take(count)
        ?.toList() ?: listOf()
}