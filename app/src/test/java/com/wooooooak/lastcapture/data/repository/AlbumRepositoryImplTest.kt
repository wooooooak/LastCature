package com.wooooooak.lastcapture.data.repository

import com.wooooooak.lastcapture.data.model.AlbumLocal
import com.wooooooak.lastcapture.data.model.ImageLocal
import com.wooooooak.lastcapture.data.source.local.AlbumLocalDataSource
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class AlbumRepositoryImplTest : BehaviorSpec({

    val testDispatcher = TestCoroutineDispatcher()

    lateinit var albumRepositoryImpl: AlbumRepository

    val albumLocalDataSource: AlbumLocalDataSource = mockk()

    val allAlbumList = listOf(
        AlbumLocal("alubum1", "", false),
        AlbumLocal("alubum2", "", false),
        AlbumLocal("alubum3", "", false),
        AlbumLocal("alubum4", "", false),
        AlbumLocal("alubum5", "", false),
        AlbumLocal("alubum6", "", false),
    )

    val selectedAlbumList = listOf(
        AlbumLocal("alubum5", "", true),
        AlbumLocal("alubum6", "", true),
    )

    beforeTest {
        Dispatchers.setMain(testDispatcher)
        albumRepositoryImpl = AlbumRepositoryImpl(albumLocalDataSource, testDispatcher)
    }

    afterTest {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    Given("albumLocalDataSource에 데이터가 존재하는 상황에서") {
        coEvery { albumLocalDataSource.getAllAlbum() } returns allAlbumList
        coEvery { albumLocalDataSource.getSelectedAlbumList() } returns selectedAlbumList
        When("getAllAlbum()이 호출될 때") {
            val result: List<AlbumLocal> = albumRepositoryImpl.getAllAlbum()
            Then("전체앨범조회와 선택된앨범조회가 발생하며 이들이 섞여서 리스트로 반환된다") {
                coVerify(exactly = 1) { albumLocalDataSource.getAllAlbum() }
                coVerify(exactly = 1) { albumLocalDataSource.getSelectedAlbumList() }
                result shouldContainAll selectedAlbumList
            }
        }
    }


    Given("album이 하나 존재하는 상황에서") {
        val album = AlbumLocal("", "")
        coEvery { albumLocalDataSource.addSelectedAlbum(album) } returns Unit
        coEvery { albumLocalDataSource.removeSelectedAlbum(album) } returns Unit
        When("addSelectedAlbum() 호출 될 때") {
            albumRepositoryImpl.addSelectedAlbum(album)
            Then("dataSource의 add 함수가 호출된다") {
                coVerify(exactly = 1) { albumLocalDataSource.addSelectedAlbum(album) }
            }
        }

        When("removeSelectedAlbum() 호출 될 때") {
            albumRepositoryImpl.removeSelectedAlbum(album)
            Then("datasource의 remove함수가 호출된다") {
                coVerify(exactly = 1) { albumLocalDataSource.removeSelectedAlbum(album) }
            }
        }
    }

    Given("선택된 앨범이 DB에 존재하고 그 갯수가 3개인 상황에서") {
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
        When("getSelectedImage() 호출 될 때") {
            albumRepositoryImpl.getSelectedImage(count)
            Then("dataSource가 차례대로 호출되고 3이 dataSource의 파라미터로 넘어간다") {
                coVerifyOrder {
                    albumLocalDataSource.getSelectedAlbumList()
                    albumLocalDataSource.getSelectedImageList(count, any())
                }
            }
        }
    }

    Given("선택된 앨범 갯수가 0인 상황에서") {
        coEvery { albumLocalDataSource.getSelectedAlbumList() } returns listOf()
        coEvery { albumLocalDataSource.getSelectedImageList(any(), any()) } returns listOf()
        When("getSelectedImage() 호출 될 때") {
            val result = albumRepositoryImpl.getSelectedImage(0)
            Then("dataSource 호출 없이 곧바로 빈 리스트를 반환한다") {
                coVerify(exactly = 0) { albumLocalDataSource.getSelectedAlbumList() }
                coVerify(exactly = 0) { albumLocalDataSource.getSelectedImageList(any(), any()) }
                result.isEmpty() shouldBe true
            }
        }
    }
})