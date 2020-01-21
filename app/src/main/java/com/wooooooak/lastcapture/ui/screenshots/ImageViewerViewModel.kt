package com.wooooooak.lastcapture.ui.screenshots

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wooooooak.lastcapture.MyApplication
import com.wooooooak.lastcapture.data.model.ScreenShot
import com.wooooooak.lastcapture.data.repository.ScreenShotRepository

class ImageViewerViewModel(application: Application, private val repository: ScreenShotRepository) : ViewModel() {

    private val screenShotCount = MyApplication.pref.screenShotCount

    private val selectedFolderUriSet = MyApplication.pref.selectedFolderName

    private val _defaultFloatingButtonVisibility = MutableLiveData<Boolean>(false)
    val defaultFloatingButtonVisibility: LiveData<Boolean> = _defaultFloatingButtonVisibility

    private val _floatingButtonVisibility = MutableLiveData<Boolean>(false)
    val floatingButtonVisibility: LiveData<Boolean> = _floatingButtonVisibility

    private val _showingCount = MutableLiveData<Int>(if (screenShotCount == 0) 10 else screenShotCount)
    val showingCount: LiveData<Int> = _showingCount

    val screenShots = Transformations.map(_showingCount) {
        try {
            repository.getScreenShot(application, it, selectedFolderUriSet)
        } catch (e: Exception) {
            listOf<ScreenShot>()
        }
    }

    fun refreshItem() {
        _showingCount.value = screenShotCount
    }

    fun setShowingCount(value: Int) {
        MyApplication.pref.screenShotCount = value
        _showingCount.value = value
    }

    fun changeFloatingButtonVisibility() {
        _floatingButtonVisibility.value?.let {
            _floatingButtonVisibility.value = !it
        }
    }

    fun setAllFloatingButtonVisibility(isVisible: Boolean) {
        _defaultFloatingButtonVisibility.value = isVisible
        if (!isVisible) {
            _floatingButtonVisibility.value = isVisible
        }
    }

}