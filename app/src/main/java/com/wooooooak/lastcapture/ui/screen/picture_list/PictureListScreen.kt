package com.wooooooak.lastcapture.ui.screen.picture_list

import androidx.compose.foundation.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.wooooooak.lastcapture.ui.screen.Screen

@Composable
fun PictureListScreen(navController: NavController) {
    Button(onClick = { navController.navigate("${Screen.PictureDetail.route}/10") }) {
        Text(text = "Navigate to image {10}")
    }
}