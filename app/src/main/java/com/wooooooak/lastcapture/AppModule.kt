package com.wooooooak.lastcapture

import com.wooooooak.lastcapture.data.ScreenShotRepository
import com.wooooooak.lastcapture.ui.screenshots.ShowingLastThreeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ShowingLastThreeViewModel(get()) }

    single { ScreenShotRepository() }
}