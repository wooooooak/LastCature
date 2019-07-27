package com.wooooooak.lastcapture.ui.album

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wooooooak.lastcapture.data.Album
import com.wooooooak.lastcapture.utilities.GalleryUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumSelectorViewModel(application: Application) : AndroidViewModel(application) {

    val TAG = "AlbumSelectorView"
    private val _albumList = MutableLiveData<List<Album>>()
    val albumList: LiveData<List<Album>>
        get() = _albumList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _albumList.postValue(GalleryUtil.getAllAlbumList(application))
        }
    }

}