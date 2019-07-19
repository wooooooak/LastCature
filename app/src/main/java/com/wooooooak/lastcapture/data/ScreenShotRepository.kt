package com.wooooooak.lastcapture.data

import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.File

class ScreenShotRepository {
    fun getScreenShot(count: Int): LiveData<List<File>> {
        val path = Environment.getExternalStorageDirectory().absolutePath + "/DCIM/Screenshots"
        val screenShots = File(path).listFiles()?.apply {
            sortBy { it.lastModified() } }?.takeLast(count)?.reversed()
        return MutableLiveData<List<File>>().apply { value = screenShots }
    }
}