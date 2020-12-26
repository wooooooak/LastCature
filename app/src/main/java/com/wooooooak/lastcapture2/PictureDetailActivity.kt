package com.wooooooak.lastcapture2

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.ui.platform.setContent
import com.wooooooak.lastcapture2.ui.LastCaptureTheme
import com.wooooooak.lastcapture2.ui.component.picture_detail.PictureDetailScreen

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