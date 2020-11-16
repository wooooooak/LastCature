package com.wooooooak.lastcapture.data.repository

import com.google.common.truth.Truth
import com.wooooooak.lastcapture.data.model.AlbumLocal
import com.wooooooak.lastcapture.data.source.local.AlbumLocalDataSource
import io.mockk.coEvery
import io.mockk.coVerify
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

}