package com.wooooooak.lastcapture2.data.dao

import androidx.room.*
import com.wooooooak.lastcapture2.data.model.AlbumLocal

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSelectedAlbum(albumLocal: AlbumLocal)

    @Query("SELECT * FROM AlbumLocal")
    suspend fun getSelectedAlbumList(): List<AlbumLocal>

    @Delete
    suspend fun removeSelectedAlbum(albumLocal: AlbumLocal)
}