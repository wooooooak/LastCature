package com.wooooooak.lastcapture.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wooooooak.lastcapture.utilities.getRecentFileList
import java.io.File

class ScreenShotRepository {

    fun getScreenShot(count: Int, folderUriSet: Set<String>): LiveData<List<File>> {
        val screenShots = mutableListOf<File>()
        folderUriSet.forEach { imagePath ->
            screenShots.addAll(0, File(imagePath).getRecentFileList(count))
        }

        val targetScreenShot = screenShots.apply {
            sortBy { it.lastModified() }
        }.takeLast(count).reversed()
        return MutableLiveData<List<File>>().apply { value = targetScreenShot }
    }
}