package com.wooooooak.lastcapture.data.dao

import androidx.room.*
import com.wooooooak.lastcapture.data.model.AlbumLocal

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSelectedAlbum(albumLocal: AlbumLocal)

    @Query("SELECT * FROM AlbumLocal")
    suspend fun getSelectedAlbumList(): List<AlbumLocal>

    @Delete
    suspend fun removeSelectedAlbum(albumLocal: AlbumLocal)
}