package com.wooooooak.lastcapture.ui.component.album_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.transform.RoundedCornersTransformation
import com.wooooooak.lastcapture.ui.component.LazyGirdViewFor
import com.wooooooak.lastcapture.ui.model.AlbumModel
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun AlbumListScreen(viewModel: AlbumListViewModel) {
    val allAlbumLocal: List<AlbumModel> by viewModel.allAlbum.observeAsState(initial = listOf())
    Column {
        Divider(thickness = 8.dp, color = MaterialTheme.colors.surface)
        Text(text = "앨범을 선택해주세요", modifier = Modifier.padding(start = 8.dp))
        Divider(thickness = 10.dp, color = MaterialTheme.colors.surface)
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
                CoilImage(
                    data = album.image.toUri(),
                    contentScale = ContentScale.Crop,
                    requestBuilder = {
                        crossfade(true)
                        transformations(RoundedCornersTransformation())
                    },
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