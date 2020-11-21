package com.wooooooak.lastcapture

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.ui.platform.setContent
import com.wooooooak.lastcapture.ui.LastCaptureTheme
import com.wooooooak.lastcapture.ui.component.picture_detail.PictureDetailScreen

class PictureDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContent {
            LastCaptureTheme {
                Scaffold {
                    PictureDetailScreen(uri = intent.getStringExtra("uri"))
                }
            }
        }
    }
}