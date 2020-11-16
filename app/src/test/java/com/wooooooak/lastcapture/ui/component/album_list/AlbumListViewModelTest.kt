package com.wooooooak.lastcapture.ui.component.album_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.wooooooak.lastcapture.data.model.AlbumLocal
import com.wooooooak.lastcapture.fake.FakeAlbumRepository
import com.wooooooak.lastcapture.mapToUi
import getOrAwaitValue
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi // LOOK HERE
class AlbumListViewModelTest {

    @ObsoleteCoroutinesApi
//    private val testDispatcher = newSingleThreadContext("UI main")
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var albumListViewModel: AlbumListViewModel

    private val album1 = AlbumLocal("album1", "image1", false)
    private val album2 = AlbumLocal("album2", "image2", false)
    private val album3 = AlbumLocal("album3", "image3", true)
    private val album4 = AlbumLocal("album4", "image4", true)

    private var albumRepository: FakeAlbumRepository = spyk(FakeAlbumRepository())

    @ObsoleteCoroutinesApi
    @Before
    fun setUp() = runBlockingTest {
        albumRepository.addAlbumList(listOf(album1, album2, album3, album4))
        Dispatchers.setMain(testDispatcher)
        coroutineScope {
            albumListViewModel = AlbumListViewModel(albumRepository)
        }
    }

    @After
    fun tearDown() {
        albumRepository.clear()
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `어떤 앨범을 선택하든 Repository의 getSelectedImage함수가 호출된다`() = runBlockingTest {
        albumListViewModel.onClickAlbum(album2.mapToUi())
        albumListViewModel.selectedImage.getOrAwaitValue()
        coVerify(exactly = 1) { albumRepository.getSelectedImage(any()) }
    }

    @Test
    fun `선택안된앨범을클릭하면_LiveData가변경되며,기존데이터에서선택된앨범갯수가증가하고선택안된건감소`() = runBlockingTest {
        albumListViewModel.onClickAlbum(album2.mapToUi())
        val value = albumListViewModel.allAlbum.getOrAwaitValue()
        assert(value.count { it.isClicked } == 3)
        assert(value.count { !it.isClicked } == 1)
    }

    @Test
    fun `선택된앨범을클릭_onClickAlbum()호출_선택된앨범갯수감소&선택안된건증가`() = runBlockingTest {
        albumListViewModel.onClickAlbum(album3.mapToUi())
        val value = albumListViewModel.allAlbum.getOrAwaitValue()
        assert(value.count { it.isClicked } == 1)
        assert(value.count { !it.isClicked } == 3)
    }

    @Test
    fun `뷰모델이 생성된 직후_fetchAllAlbum()이 호출되고_LiveData에 데이터가 반영된다`() {
        val value = albumListViewModel.allAlbum.getOrAwaitValue()
        Truth.assertThat(value).isNotEmpty()
    }
}
