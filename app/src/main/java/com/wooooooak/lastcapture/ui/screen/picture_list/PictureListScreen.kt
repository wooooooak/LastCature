package com.wooooooak.lastcapture.ui.screen.picture_list

import androidx.compose.foundation.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable

@Composable
fun PictureListScreen(navToPictureDetail: (pictureId: Int) -> Unit) {
    Button(onClick = { navToPictureDetail(10) }) {
        Text(text = "Navigate to image {10}")
    }
}