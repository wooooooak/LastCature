package com.wooooooak.lastcapture.ui.screenshots

import androidx.lifecycle.ViewModel
import com.wooooooak.lastcapture.MyApplication
import com.wooooooak.lastcapture.data.ScreenShotRepository

class ShowingLastThreeViewModel(repository: ScreenShotRepository) : ViewModel() {
    private val screenShotCount = MyApplication.pref.screenShotCount
    private val selectedFolderUriSet = MyApplication.pref.selectedFolderUris

    val screenShots = repository.getScreenShot(screenShotCount, selectedFolderUriSet)

}