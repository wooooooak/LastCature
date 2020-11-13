package com.wooooooak.lastcapture.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wooooooak.lastcapture.data.model.AlbumLocal

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSelectedAlbum(albumLocal: AlbumLocal)

    @Query("SELECT * FROM AlbumLocal")
    suspend fun getSelectedAlbumList(): List<AlbumLocal>
}