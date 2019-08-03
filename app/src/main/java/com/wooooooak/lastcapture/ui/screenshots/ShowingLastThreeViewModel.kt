package com.wooooooak.lastcapture.ui.screenshots

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wooooooak.lastcapture.MyApplication
import com.wooooooak.lastcapture.data.ScreenShotRepository

class ShowingLastThreeViewModel(private val repository: ScreenShotRepository) : ViewModel() {
    private val screenShotCount = MyApplication.pref.screenShotCount
    private val selectedFolderUriSet = MyApplication.pref.selectedFolderUris

    private val _floatingButtonVisibility = MutableLiveData<Boolean>().apply { value = false }
    val floatingButtonVisibility: LiveData<Boolean>
        get() = _floatingButtonVisibility

    private val _showingCount = MutableLiveData<Int>().apply { value = screenShotCount }
    val showingCount: LiveData<Int>
        get() = _showingCount

    val screenShots = Transformations.map(_showingCount) {
        repository.getScreenShot(it, selectedFolderUriSet)
    }

    fun setShowingCount(value: Int) {
        MyApplication.pref.screenShotCount = value
        _showingCount.value = value
    }

    fun setFloatingButtonVisibility() {
        _floatingButtonVisibility.value?.let {
            _floatingButtonVisibility.value = !it
        }
    }

}