package com.wooooooak.lastcapture2.ui.component.album_list

import androidx.lifecycle.*
import com.wooooooak.lastcapture2.data.repository.AlbumRepository
import com.wooooooak.lastcapture2.mapToLocal
import com.wooooooak.lastcapture2.mapToUi
import com.wooooooak.lastcapture2.ui.model.AlbumModel
import com.wooooooak.lastcapture2.ui.model.ImageModel
import kotlinx.coroutines.launch

class AlbumListViewModel(
    private val albumRepository: AlbumRepository,
) : ViewModel() {
    private val _allAlbum = MutableLiveData<List<AlbumModel>>()
    val allAlbum: LiveData<List<AlbumModel>> = _allAlbum

    val selectedImage: LiveData<List<ImageModel>> = allAlbum.switchMap {
        liveData {
            emit(albumRepository.getSelectedImage(15).map { it.mapToUi() })
        }
    }

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
        }
    }

    private suspend fun fetchAllAlbum() {
        _allAlbum.value = albumRepository.getAllAlbum().map { it.mapToUi() }
    }

}