package com.wooooooak.lastcapture.utilities

import android.util.Log
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

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

val File.lastModifiedTime: String
    get() {
        val time = SimpleDateFormat("yyyy.MM.dd").format(Date(this.lastModified()))
        Log.d("FileExt", time)
        return time
    }
