package com.wooooooak.lastcapture.ui.component.album_list

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wooooooak.lastcapture.ui.component.LazyGirdViewFor
import com.wooooooak.lastcapture.ui.model.AlbumModel

@Composable
fun AlbumListScreen(viewModel: AlbumListViewModel) {
    val allAlbumLocal: List<AlbumModel> by viewModel.allAlbumLocal.observeAsState(initial = listOf())
    Column {
        Divider(thickness = 8.dp, color = Color.White)
        Text(text = "앨범을 선택해주세요", modifier = Modifier.padding(start = 8.dp))
        Divider(thickness = 10.dp, color = Color.White)
        LazyGirdViewFor(
            items = allAlbumLocal,
            columnCount = 2,
            itemModifier = Modifier.padding(8.dp)
        ) { album ->
            Surface {
                Column {
                    Text(text = album.name)
                    Text(text = album.imageUri.toString())
                }
            }
        }
    }
}
