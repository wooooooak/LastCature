package com.wooooooak.lastcapture.ui.component.picture_detail

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.core.net.toUri
import coil.transform.RoundedCornersTransformation
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun PictureDetailScreen(uri: String?) {
    Surface(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
        CoilImage(
            data = uri?.toUri() ?: "",
            contentScale = ContentScale.Fit,
            requestBuilder = {
                crossfade(true)
                transformations(RoundedCornersTransformation())
            },
        )
    }
}