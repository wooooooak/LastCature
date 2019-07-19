package com.wooooooak.lastcapture.ui.screenshots

import androidx.lifecycle.ViewModel
import com.wooooooak.lastcapture.data.ScreenShotRepository

class ShowingLastThreeViewModel(repository: ScreenShotRepository) : ViewModel() {

    val screenShots = repository.getScreenShot(6)

}