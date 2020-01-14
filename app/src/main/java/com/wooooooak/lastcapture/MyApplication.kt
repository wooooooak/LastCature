package com.wooooooak.lastcapture

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.wooooooak.lastcapture.utilities.LCPreference
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MyApplication: Application() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    companion object {
        lateinit var pref: LCPreference
    }

    override fun onCreate(){
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        pref = LCPreference(applicationContext)
        initLogger()
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }

    private fun initLogger() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false) // (Optional) Whether to show thread info or not. Default true
            .methodCount(1)
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }
}