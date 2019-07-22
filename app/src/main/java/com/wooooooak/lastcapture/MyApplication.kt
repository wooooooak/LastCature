package com.wooooooak.lastcapture

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication(): Application() {
    override fun onCreate(){
        super.onCreate()
        startKoin {
            // Android context
            androidContext(this@MyApplication)
            // modules
            modules(appModule)
        }
    }
}