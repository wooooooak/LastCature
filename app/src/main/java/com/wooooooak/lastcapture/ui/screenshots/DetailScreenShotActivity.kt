package com.wooooooak.lastcapture.ui.screenshots

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.wooooooak.lastcapture.R
import com.wooooooak.lastcapture.databinding.ActivityDetailScreenShotBinding
import kotlinx.android.synthetic.main.activity_detail_screen_shot.*

class DetailScreenShotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailScreenShotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        val filePath = intent.extras?.get(SHARED_FILE_URI) ?: ""
        val imageView = thumbnail

        Glide.with(imageView.context)
            .load(filePath)
            .into(imageView)
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_screen_shot)
    }

    companion object {
        const val SHARED_FILE_URI = "shared_file_uri"
    }
}
