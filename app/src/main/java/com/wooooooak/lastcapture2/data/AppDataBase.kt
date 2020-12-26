package com.wooooooak.lastcapture2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wooooooak.lastcapture2.data.dao.AlbumDao
import com.wooooooak.lastcapture2.data.model.AlbumLocal

@Database(entities = [AlbumLocal::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao

    companion object {
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase? {
            if (INSTANCE == null) {
                synchronized(AppDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "appDataBase",
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}