package com.wooooooak.lastcapture.ui.album

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wooooooak.lastcapture.data.Album
import com.wooooooak.lastcapture.utilities.GalleryUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumSelectorViewModel(application: Application) : AndroidViewModel(application) {

    val TAG = "AlbumSelectorView"
    val albumList = MutableLiveData<List<Album>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            albumList.postValue(GalleryUtil.getAlbumList(application))
        }
    }

}