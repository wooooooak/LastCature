package com.wooooooak.lastcapture.ui.component.album_list

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.glide.GlideImage
import com.wooooooak.lastcapture.ui.component.LazyGirdViewFor
import com.wooooooak.lastcapture.ui.model.AlbumModel
import com.wooooooak.lastcapture.ui.model.ImageModel

@Composable
fun AlbumListScreen(viewModel: AlbumListViewModel) {
    val allAlbumLocal: List<AlbumModel> by viewModel.allAlbum.observeAsState(initial = listOf())
    val imageList: List<ImageModel> by viewModel.selectedImage.observeAsState(initial = listOf())
    Column {
        Divider(thickness = 8.dp, color = Color.White)
        Text(text = "앨범을 선택해주세요", modifier = Modifier.padding(start = 8.dp))
        Divider(thickness = 10.dp, color = Color.White)
        LazyGirdViewFor(
            items = allAlbumLocal,
            columnCount = 2,
            itemModifier = Modifier.padding(8.dp)
        ) { album ->
            AlbumItem(album) {
                viewModel.onClickAlbum(it)
            }
        }
    }
}

@Composable
fun AlbumItem(album: AlbumModel, onClickAlbum: (AlbumModel) -> Unit) {
    Surface(modifier = Modifier.clickable(onClick = { onClickAlbum(album) })) {
        Column {
            Box(
                modifier = Modifier
                    .size(170.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                GlideImage(
                    imageModel = album.image.toUri(),
                    requestOptions = RequestOptions()
                        .override(256, 256)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop(),
                )
                if (album.isClicked) {
                    Icon(
                        asset = Icons.Filled.Favorite,
                        tint = Color.Red,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 16.dp, top = 4.dp)
                    )
                }
            }
            Text(text = album.name)
        }
    }
}