package com.wooooooak.lastcapture.ui.component.album_list

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wooooooak.lastcapture.ui.model.AlbumModel

@Composable
fun AlbumListScreen(viewModel: AlbumListViewModel) {
    val allAlbumLocal: List<AlbumModel> by viewModel.allAlbumLocal.observeAsState(initial = listOf())
    Column {
        Divider(thickness = 8.dp, color = Color.White)
        Text(text = "앨범을 선택해주세요", modifier = Modifier.padding(start = 8.dp))
        Divider(thickness = 10.dp, color = Color.White)
        LazyColumnFor(items = allAlbumLocal) { album ->
            Surface(modifier = Modifier.padding(8.dp)) {
                Text(text = album.name, style = TextStyle(fontSize = 16.sp))
            }
        }
    }
}