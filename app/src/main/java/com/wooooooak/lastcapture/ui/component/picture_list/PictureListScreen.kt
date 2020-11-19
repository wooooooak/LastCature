package com.wooooooak.lastcapture.ui.component.picture_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.transform.RoundedCornersTransformation
import com.wooooooak.lastcapture.ui.component.LazyGirdViewFor
import com.wooooooak.lastcapture.ui.component.album_list.AlbumListViewModel
import com.wooooooak.lastcapture.ui.model.ImageModel
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun PictureListScreen(
    albumListViewModel: AlbumListViewModel,
    navToPictureDetail: (pictureId: Int) -> Unit,
) {
    val imageList: List<ImageModel> by albumListViewModel.selectedImage.observeAsState(initial = listOf())

    Column {
        Button(onClick = { navToPictureDetail(10) }) {
            Text(text = "Navigate to image {10}")
        }
        LazyGirdViewFor(
            items = imageList,
            columnCount = 2,
            itemModifier = Modifier.padding(8.dp)
        ) { image ->
            ImageItem(image = image, onClickImage = { })
        }
    }
}

@Composable
fun ImageItem(image: ImageModel, onClickImage: (ImageModel) -> Unit) {
    Surface(modifier = Modifier.clickable(onClick = { onClickImage(image) })) {
        Column {
            Box(
                modifier = Modifier
                    .preferredHeight(240.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                CoilImage(
                    data = image.imagePath.toUri(),
                    contentScale = ContentScale.Fit,
                    requestBuilder = {
                        crossfade(true)
                        transformations(RoundedCornersTransformation())
                    },
                )
            }
            Text(text = image.name)
        }
    }
}