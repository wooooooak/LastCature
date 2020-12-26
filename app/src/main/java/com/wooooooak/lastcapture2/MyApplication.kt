package com.wooooooak.lastcapture2

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy


class MyApplication: Application() {

    override fun onCreate(){
        initLogger()
        super.onCreate()
//        startKoin {
//            androidContext(this@MyApplication)
//        }
    }

    private fun initLogger() {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false) // (Optional) Whether to show thread info or not. Default true
            .methodCount(1)
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }
}