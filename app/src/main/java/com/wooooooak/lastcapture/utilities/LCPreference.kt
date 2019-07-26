package com.wooooooak.lastcapture.utilities

import android.content.Context
import androidx.core.content.edit

class LCPreference internal constructor (context: Context) {
    private val FILE_NAME = "lastCapture"
    private val SELECTED_FOLDER = "selected folder"
    private val SCREENSHOT_COUNT = "screen shot count"
    private val DEFAULT_FOLDER = "ScreenShots"
    private val DEFAULT_SCREENSHOT_COUNT = 8


    private val pref = context.getSharedPreferences(FILE_NAME, 0)

    var selectedFolder: Set<String>
        get() = pref.getStringSet(SELECTED_FOLDER, setOf(DEFAULT_FOLDER))
        set(value) = pref.edit {
            putStringSet(SELECTED_FOLDER, value)
        }

    var screenShotCount: Int
        get() = pref.getInt(SCREENSHOT_COUNT, DEFAULT_SCREENSHOT_COUNT)
        set(value) = pref.edit {
            putInt(SCREENSHOT_COUNT, value)
        }
}