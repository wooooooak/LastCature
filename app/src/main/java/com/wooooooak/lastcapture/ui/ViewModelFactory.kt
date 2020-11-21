package com.wooooooak.lastcapture.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wooooooak.lastcapture.data.repository.AlbumRepository
import com.wooooooak.lastcapture.ui.component.album_list.AlbumListViewModel

class AlbumListViewModelFactory(
    private val param: AlbumRepository,
) :
    ViewModelProvider
    .Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AlbumListViewModel::class.java)) {
            AlbumListViewModel(param) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}