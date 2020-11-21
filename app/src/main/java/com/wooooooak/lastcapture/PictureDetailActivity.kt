package com.wooooooak.lastcapture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.ui.platform.setContent
import com.wooooooak.lastcapture.ui.LastCaptureTheme
import com.wooooooak.lastcapture.ui.component.picture_detail.PictureDetailScreen

class PictureDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LastCaptureTheme {
                Scaffold {
                    PictureDetailScreen(uri = intent.getStringExtra("uri"))
                }
            }
        }
    }
}