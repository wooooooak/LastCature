package com.wooooooak.lastcapture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.wooooooak.lastcapture.ui.LastCaptureTheme
import com.wooooooak.lastcapture.ui.screen.Screen
import com.wooooooak.lastcapture.ui.screen.album_list.AlbumListScreen
import com.wooooooak.lastcapture.ui.screen.picture_detail.PictureDetailScreen
import com.wooooooak.lastcapture.ui.screen.picture_list.PictureListScreen

class MainActivity : AppCompatActivity() {
    private val bottomNavigationItem = listOf(
        Screen.PictureList,
        Screen.AlbumList,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            LastCaptureTheme {
                Scaffold(
                    bottomBar = {
                        BottomNavigation {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
                            bottomNavigationItem.forEach { screen ->
                                BottomNavigationItem(
                                    icon = { Image(Icons.Filled.Favorite) },
                                    label = { Text(text = screen.name) },
                                    selected = screen.route == currentRoute,
                                    onClick = {
                                        navController.popBackStack()
                                        if (currentRoute != screen.route) {
                                            navController.navigate(screen.route)
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) {
                    NavHost(navController, startDestination = Screen.PictureList.route) {
                        composable(Screen.PictureList.route) {
                            PictureListScreen { pictureId ->
                                navController.navigate("${Screen.PictureDetail.route}/$pictureId")
                            }
                        }
                        composable(Screen.AlbumList.route) { AlbumListScreen() }
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

