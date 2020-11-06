package com.wooooooak.lastcapture.ui.screen.picture_detail

import androidx.compose.foundation.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun PictureDetailScreen(navController: NavController, id: Int?) {
    Text(text = id.toString())
}