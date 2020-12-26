package com.wooooooak.lastcapture2.data.source.local

import android.content.ContentResolver
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth
import com.wooooooak.lastcapture2.data.AppDataBase
import com.wooooooak.lastcapture2.data.model.AlbumLocal
import io.mockk.mockk
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
@MediumTest
class AlbumLocalDataSourceTest {
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var albumLocalDataSource: AlbumDataSource
    private lateinit var database: AppDataBase
    private var contentResolver: ContentResolver = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).allowMainThreadQueries()
            .build()

        albumLocalDataSource = AlbumLocalDataSource(contentResolver, database.albumDao())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `선택한 albumNameList가 비어있을 때 getSelectedImageList()가 호출되면 빈 list를 반환한다`() = runBlockingTest {
        val count = 0
        val albumNameList = listOf<String>()
        val result = albumLocalDataSource.getSelectedImageList(count, albumNameList)

        Truth.assertThat(result).isEmpty()
    }

    @Test
    fun `새로운 앨범을 가지고 addSelectedAlbum()를 호출하면 getSelectedAlbumList()를 했을 때 해당 앨범이 반환된다`() = runBlockingTest {
        val newAlbum = AlbumLocal(name = "newAlbum1", imageResource = "aa", selected = true)
        albumLocalDataSource.addSelectedAlbum(newAlbum)
        val savedAlbum = albumLocalDataSource.getSelectedAlbumList()

        Truth.assertThat(savedAlbum).contains(newAlbum)
    }

    @Test
    fun `앨범이 db에 존재할 때 removeSelectedAlbum함수가 호출되면 getSelectedAlbumList()의 결과물에 해당 앨범이 존재하지 않는다`() = runBlockingTest {
        val newAlbum = AlbumLocal(name = "newAlbum1", imageResource = "aa", selected = true)
        val newAlbum2 = AlbumLocal(name = "newAlbum2", imageResource = "bb", selected = true)
        albumLocalDataSource.addSelectedAlbum(newAlbum)
        albumLocalDataSource.addSelectedAlbum(newAlbum2)
        albumLocalDataSource.removeSelectedAlbum(newAlbum)
        val savedAlbum = albumLocalDataSource.getSelectedAlbumList()

        Truth.assertThat(savedAlbum).doesNotContain(newAlbum)
    }

}