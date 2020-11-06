package com.wooooooak.lastcapture.ui.screen

// TODO name을 resource id로 바꾸기
sealed class Screen(val route: String, val name: String) {
    object PictureDetail : Screen("picture_detail", "picture detail")
    object PictureList : Screen("picture_list", "picture list")
    object AlbumList : Screen("album_list", "album list")
}
