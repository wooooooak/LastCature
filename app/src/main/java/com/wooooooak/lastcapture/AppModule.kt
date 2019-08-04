package com.wooooooak.lastcapture

import com.wooooooak.lastcapture.data.ScreenShotRepository
import com.wooooooak.lastcapture.ui.album.AlbumSelectorViewModel
import com.wooooooak.lastcapture.ui.screenshots.ImageViewerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ImageViewerViewModel(get()) }
    viewModel { AlbumSelectorViewModel(get()) }

    single { ScreenShotRepository() }
}