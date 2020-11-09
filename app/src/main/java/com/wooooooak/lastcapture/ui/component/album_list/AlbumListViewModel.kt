package com.wooooooak.lastcapture.ui.component.album_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.wooooooak.lastcapture.data.repository.AlbumRepository
import com.wooooooak.lastcapture.mapToUi
import com.wooooooak.lastcapture.ui.model.AlbumModel

class AlbumListViewModel(
    private val albumRepository: AlbumRepository,
) : ViewModel() {
    val allAlbumLocal: LiveData<List<AlbumModel>> = liveData {
        emit(albumRepository.getAllAlbum().map { it.mapToUi() })
    }

}