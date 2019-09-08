package com.wooooooak.lastcapture.ui.screenshots

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.crashlytics.android.Crashlytics
import com.wooooooak.lastcapture.MyApplication
import com.wooooooak.lastcapture.data.ScreenShotRepository

class ImageViewerViewModel(private val repository: ScreenShotRepository) : ViewModel() {
    private val screenShotCount = MyApplication.pref.screenShotCount
    private val selectedFolderUriSet = MyApplication.pref.selectedFolderUris

    private val _defaultFloatingButtonVisibility =
        MutableLiveData<Boolean>().apply { value = false }
    val defaultFloatingButtonVisibility: LiveData<Boolean> = _defaultFloatingButtonVisibility

    private val _floatingButtonVisibility = MutableLiveData<Boolean>().apply { value = false }
    val floatingButtonVisibility: LiveData<Boolean> = _floatingButtonVisibility

    private val _showingCount = MutableLiveData<Int>().apply { value = screenShotCount }
    val showingCount: LiveData<Int> = _showingCount

    val screenShots = Transformations.map(_showingCount) {
        repository.getScreenShot(it, selectedFolderUriSet)
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