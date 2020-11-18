package com.wooooooak.lastcapture.data.repository

import com.google.common.truth.Truth
import com.wooooooak.lastcapture.data.model.AlbumLocal
import com.wooooooak.lastcapture.data.model.ImageLocal
import com.wooooooak.lastcapture.data.source.local.AlbumLocalDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class AlbumRepositoryImplTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var albumRepositoryImpl: AlbumRepository

    private var albumLocalDataSource: AlbumLocalDataSource = mockk()

    private val allAlbumList = listOf(
        AlbumLocal("alubum1", "", false),
        AlbumLocal("alubum2", "", false),
        AlbumLocal("alubum3", "", false),
        AlbumLocal("alubum4", "", false),
        AlbumLocal("alubum5", "", false),
        AlbumLocal("alubum6", "", false),
    )

    private val selectedAlbumList = listOf(
        AlbumLocal("alubum5", "", true),
        AlbumLocal("alubum6", "", true),
    )

    @Before
    fun setUp() = runBlockingTest {
        Dispatchers.setMain(testDispatcher)
        albumRepositoryImpl = AlbumRepositoryImpl(albumLocalDataSource, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `getAllAlbum호출시 전체앨범조회와 선택된앨범조회가 발생하며 이들이 섞여서 리스트로 반환된다`() = runBlockingTest {
        coEvery { albumLocalDataSource.getAllAlbum() } returns allAlbumList
        coEvery { albumLocalDataSource.getSelectedAlbumList() } returns selectedAlbumList

        val result: List<AlbumLocal> = albumRepositoryImpl.getAllAlbum()
        coVerify(exactly = 1) { albumLocalDataSource.getAllAlbum() }
        coVerify(exactly = 1) { albumLocalDataSource.getSelectedAlbumList() }

        Truth.assertThat(result).containsAtLeastElementsIn(selectedAlbumList)
    }

    @Test
    fun `addSelectedAlbum호출시 datasource의 add함수가 호출된다`() = runBlockingTest {
        val album = AlbumLocal("", "")
        coEvery { albumLocalDataSource.addSelectedAlbum(album) } returns Unit
        albumRepositoryImpl.addSelectedAlbum(album)

        coVerify(exactly = 1) { albumLocalDataSource.addSelectedAlbum(album) }
    }

    @Test
    fun `removeSelectedAlbum호출시 datasource의 remove함수가 호출된다`() = runBlockingTest {
        val album = AlbumLocal("", "")
        coEvery { albumLocalDataSource.removeSelectedAlbum(album) } returns Unit
        albumRepositoryImpl.removeSelectedAlbum(album)

        coVerify(exactly = 1) { albumLocalDataSource.removeSelectedAlbum(album) }
    }

    @Test
    fun `getSelectedImage호출 시, 선택된 앨범이 존재하고 count가 3개라면 dataSource가 차례대로 호출된다`() = runBlockingTest {
        val allAlbums = listOf(
            AlbumLocal("alubum1", "", false),
            AlbumLocal("alubum2", "", false),
        )
        val selectedAlbums = listOf(
            AlbumLocal("alubum5", "", true),
            AlbumLocal("alubum6", "", true),
            AlbumLocal("alubum7", "", true),
        )
        val imageList = listOf(
            ImageLocal(name = "", imagePath = ""),
            ImageLocal(name = "", imagePath = ""),
            ImageLocal(name = "", imagePath = ""),
        )
        val count = 3
        coEvery { albumLocalDataSource.getSelectedAlbumList() } returns allAlbums + selectedAlbums
        coEvery { albumLocalDataSource.getSelectedImageList(count, any()) } returns imageList

        albumRepositoryImpl.getSelectedImage(count)

        coVerifyOrder {
            albumLocalDataSource.getSelectedAlbumList()
            albumLocalDataSource.getSelectedImageList(count, any())
        }
    }

    @Test
    fun `getSelectedImage호출 시 count가 0이라면 dataSource 호출 없이 곧바로 빈 리스트를 반환한다`() = runBlockingTest {
        coEvery { albumLocalDataSource.getSelectedAlbumList() } returns listOf()
        coEvery { albumLocalDataSource.getSelectedImageList(any(), any()) } returns listOf()

        val result = albumRepositoryImpl.getSelectedImage(0)

        coVerify(exactly = 0) { albumLocalDataSource.getSelectedAlbumList() }
        coVerify(exactly = 0) { albumLocalDataSource.getSelectedImageList(any(), any()) }

        Truth.assertThat(result.isEmpty())
    }
}