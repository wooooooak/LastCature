package com.wooooooak.lastcapture.ui.screenshots

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.wooooooak.lastcapture.R
import kotlinx.android.synthetic.main.activity_detail_screen_shot.*

class DetailScreenShotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_screen_shot)

        val filePath = intent.extras?.get(SHARED_FILE_PATH) ?: ""
        val imageView = thumbnail

        imageView.setOnClickListener {
            finish()
        }
        Glide.with(imageView.context)
            .load(filePath)
            .into(imageView)

    }

    companion object {
        const val SHARED_FILE_PATH = "shared_file_path"
    }
}
