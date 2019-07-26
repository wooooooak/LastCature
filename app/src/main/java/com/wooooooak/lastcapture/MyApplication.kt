package com.wooooooak.lastcapture

import android.app.Application
import com.wooooooak.lastcapture.utilities.LCPreference
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    companion object {
        lateinit var pref: LCPreference
    }

    override fun onCreate(){
        pref = LCPreference(applicationContext)
        super.onCreate()
        startKoin {
            // Android context
            androidContext(this@MyApplication)
            // modules
            modules(appModule)
        }
    }
}