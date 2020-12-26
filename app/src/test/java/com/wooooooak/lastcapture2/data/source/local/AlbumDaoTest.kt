package com.wooooooak.lastcapture2.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.wooooooak.lastcapture2.data.AppDataBase
import com.wooooooak.lastcapture2.data.model.AlbumLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class AlbumDaoTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDataBase

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).allowMainThreadQueries()
            .build()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
        database.close()
    }

    @Test
    fun `앨범 하나를 추가하는 기능 테스트`() = runBlockingTest {

        // GIVEN - add a album
        val album = AlbumLocal("test_album", "res", selected = true)
        database.albumDao().addSelectedAlbum(album)

        // WHEN - Get the all selected album from the database
        val selectedAlbumList = database.albumDao().getSelectedAlbumList()

        // THEN - The loaded data contains the expected values
        Truth.assertThat(selectedAlbumList).containsExactly(album)
    }

    @Test
    fun `앨범 하나를 제거하는 기능 테스트`() = runBlockingTest {

        // Given a albums added
        val toDeleteAlbum = AlbumLocal("toDelete", "res", selected = true)
        val album = AlbumLocal("album", "res", selected = true)
        database.albumDao().addSelectedAlbum(toDeleteAlbum)
        database.albumDao().addSelectedAlbum(album)

        // WHEN 특정 앨범을 삭제하면
        database.albumDao().removeSelectedAlbum(toDeleteAlbum)
        val result = database.albumDao().getSelectedAlbumList()

        // THEN load된 데이터에 해당 삭제한 데이터가 없다.
        Truth.assertThat(result).doesNotContain(toDeleteAlbum)
        Truth.assertThat(result).contains(album)
    }

}