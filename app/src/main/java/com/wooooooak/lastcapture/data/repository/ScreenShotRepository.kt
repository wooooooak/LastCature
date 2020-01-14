package com.wooooooak.lastcapture.data.repository

import com.orhanobut.logger.Logger
import com.wooooooak.lastcapture.utilities.ext.getRecentImageList
import java.io.File

class ScreenShotRepository {
    fun getScreenShot(count: Int, folderUriSet: Set<String>): List<File> {
        Logger.d(folderUriSet)
        val screenShots = mutableListOf<File>()
        folderUriSet.forEach { imagePath ->
            screenShots.addAll(0, File(imagePath).getRecentImageList(count))
        }

        return screenShots.apply {
            sortBy { it.lastModified() }
        }.takeLast(count).reversed()
    }
}