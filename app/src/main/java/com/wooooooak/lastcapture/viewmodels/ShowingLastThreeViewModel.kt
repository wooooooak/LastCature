package com.wooooooak.lastcapture.viewmodels

import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import java.io.File

class ShowingLastThreeViewModel: ViewModel() {
    init {
        val path = Environment.getExternalStorageDirectory()
//        val screenshots = File(path, "Screenshots")
        val screenshots = File(path, Environment.DIRECTORY_DCIM)
        path.list().forEach {
                Log.d("Showing", it)

        }
        screenshots.listFiles().forEach {
                Log.d("Showing", it.name)

        }
        if(screenshots.isDirectory) {
            screenshots.listFiles().forEach {
                Log.d("Showing", it.name)
            }
        }

        Log.d("ShowingLastThree", screenshots.absolutePath)
    }
}