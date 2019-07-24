package com.wooooooak.lastcapture.adapter

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.wooooooak.lastcapture.R
import java.io.File

@BindingAdapter("imageFromLocalFile")
fun bindImageFromLocalFile(view: ImageView, file: File?) {
    file?.let {
        Glide.with(view.context)
            .load(file)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("imageFromUri")
fun bindingImageFromUri(view: ImageView, uri: Uri) {
    Glide.with(view.context)
        .load(uri)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)
}

@BindingAdapter("check")
fun setBackgroundResource(view: View, isSelected: Boolean) {
    if (isSelected) {
        view.setBackgroundResource(R.drawable.border_red)
    } else {
        view.setBackgroundResource(0)
    }

}