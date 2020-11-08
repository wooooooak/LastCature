package com.wooooooak.lastcapture.ui.component.album_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.orhanobut.logger.Logger
import com.wooooooak.lastcapture.data.repository.AlbumRepository
import com.wooooooak.lastcapture.mapToUi
import com.wooooooak.lastcapture.ui.model.AlbumModel
import kotlinx.coroutines.launch

class AlbumListViewModel(
    private val albumRepository: AlbumRepository,
) : ViewModel() {
    val allAlbumLocal: LiveData<List<AlbumModel>> = liveData {
        Logger.d("111")
        emit(albumRepository.getAllAlbum().map { it.mapToUi() })
        Logger.d("2222")
    }

    init {
        viewModelScope.launch {
            val a = albumRepository.getAllAlbum().map { it.mapToUi() }
            Logger.d(a)
        }
        Logger.d("init!!")
    }
}