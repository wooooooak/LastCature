package com.wooooooak.lastcapture

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.PhotoAlbum
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.wooooooak.lastcapture.data.AppDataBase
import com.wooooooak.lastcapture.data.repository.AlbumRepository
import com.wooooooak.lastcapture.data.repository.AlbumRepositoryImpl
import com.wooooooak.lastcapture.data.source.local.AlbumLocalDataSource
import com.wooooooak.lastcapture.ui.LastCaptureTheme
import com.wooooooak.lastcapture.ui.component.Screen
import com.wooooooak.lastcapture.ui.component.album_list.AlbumListScreen
import com.wooooooak.lastcapture.ui.component.album_list.AlbumListViewModel
import com.wooooooak.lastcapture.ui.component.picture_detail.PictureDetailScreen
import com.wooooooak.lastcapture.ui.component.picture_list.PictureListScreen
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {
    private val bottomNavigationItem = listOf(
        Screen.PictureList,
        Screen.AlbumList,
    )

    class HasParamViewModelFactory(
        private val param: AlbumRepository,
    ) :
        ViewModelProvider
        .Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(AlbumListViewModel::class.java)) {
                AlbumListViewModel(param) as T
            } else {
                throw IllegalArgumentException()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            val albumListViewModel by viewModels<AlbumListViewModel> {
                HasParamViewModelFactory(
                    AlbumRepositoryImpl(
                        AlbumLocalDataSource(
                            this,
                            AppDataBase.getInstance(this)?.albumDao()
                        ),
                        Dispatchers.IO
                    )
                )
            }

            LastCaptureTheme {
                Scaffold(
                    bottomBar = {
                        BottomNavigation {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
                            bottomNavigationItem.forEach { screen ->
                                LcBottomNavigationItem(navController = navController, screen = screen, currentRoute)
                            }
                        }
                    },
                ) {
                    Surface(modifier = Modifier.padding(bottom = it.bottom)) {
                        NavHost(navController, startDestination = Screen.PictureList.route) {
                            composable(Screen.PictureList.route) {
                                PictureListScreen(albumListViewModel) { pictureId ->
                                    navController.navigate("${Screen.PictureDetail.route}/$pictureId")
                                }
                            }
                            composable(Screen.AlbumList.route) { AlbumListScreen(albumListViewModel) }
                            composable(
                                "${Screen.PictureDetail.route}/{id}",
                                arguments = listOf(navArgument("id") { type = NavType.IntType })
                            ) { navBackStackEntry ->
                                PictureDetailScreen(navBackStackEntry.arguments?.getInt("id"))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LcBottomNavigationItem(
    navController: NavController,
    screen: Screen,
    currentRoute: String?,
) {
    val isSelected = screen.route == currentRoute
    BottomNavigationItem(
        icon = { BottomNavImage(screen, isSelected) },
        label = { Text(text = screen.name) },
        selected = isSelected,
        onClick = {
            if (currentRoute != screen.route) {
                navController.popBackStack(navController.graph.startDestination, false)
                navController.navigate(screen.route)
            }
        }
    )
}

@Composable
private fun BottomNavImage(screen: Screen, isSelected: Boolean) {
    when (screen.route) {
        Screen.PictureList.route -> if (isSelected) Image(Icons.Filled.PhotoAlbum) else Image(Icons.Outlined.PhotoAlbum)
        Screen.AlbumList.route -> if (isSelected) Image(Icons.Filled.Image) else Image(Icons.Outlined.Image)
    }
}
