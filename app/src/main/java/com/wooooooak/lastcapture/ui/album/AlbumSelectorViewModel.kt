package com.wooooooak.lastcapture.ui.album

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wooooooak.lastcapture.data.model.Album
import com.wooooooak.lastcapture.utilities.getAllAlbumList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumSelectorViewModel(application: Application) : AndroidViewModel(application) {

    private val _albumList = MutableLiveData<List<Album>>()
    val albumList: LiveData<List<Album>> = _albumList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _albumList.postValue(getAllAlbumList(application))
        }
    }

}