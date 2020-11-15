package com.wooooooak.lastcapture.ui.component.album_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wooooooak.lastcapture.data.repository.AlbumRepository
import com.wooooooak.lastcapture.mapToLocal
import com.wooooooak.lastcapture.mapToUi
import com.wooooooak.lastcapture.ui.model.AlbumModel
import kotlinx.coroutines.launch

class AlbumListViewModel(
    private val albumRepository: AlbumRepository,
) : ViewModel() {

    private val _allAlbum = MutableLiveData<List<AlbumModel>>()
    val allAlbum: LiveData<List<AlbumModel>> = _allAlbum

    init {
        viewModelScope.launch {
            fetchAllAlbum()
        }
    }

    fun onClickAlbum(albumModel: AlbumModel) {
        viewModelScope.launch {
            val newAlbumModel = albumModel.copy(isClicked = !albumModel.isClicked)
            if (albumModel.isClicked) {
                albumRepository.removeSelectedAlbum(newAlbumModel.mapToLocal())
            } else {
                albumRepository.addSelectedAlbum(newAlbumModel.mapToLocal())
            }
            _allAlbum.value = allAlbum.value?.map {
                if (it.name == albumModel.name) newAlbumModel else it
            }
            println("실제 : ${_allAlbum.value}")
        }
    }

    private suspend fun fetchAllAlbum() {
        _allAlbum.value = albumRepository.getAllAlbum().map { it.mapToUi() }
    }
}