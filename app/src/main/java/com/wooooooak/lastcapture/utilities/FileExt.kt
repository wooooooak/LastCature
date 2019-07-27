package com.wooooooak.lastcapture.utilities

import java.io.File

fun File.getRecentFileList(count: Int): List<File> {
    return File(path).listFiles()?.apply {
        sortBy { it.lastModified() }
    }?.takeLast(count)?.reversed() ?: listOf()
}