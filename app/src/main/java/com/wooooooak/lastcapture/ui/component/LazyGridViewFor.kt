package com.wooooooak.lastcapture.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun <T> LazyGirdViewFor(
    items: List<T>,
    columnCount: Int,
    itemModifier: Modifier = Modifier,
    itemContent: @Composable LazyItemScope.(T) -> Unit
) {
    val chunkedItems = items.chunked(columnCount)
    LazyColumnFor(items = chunkedItems) { itemChunk ->
        Row(modifier = Modifier.fillParentMaxWidth()) {
            itemChunk.forEach { item ->
                LazyGridViewItemSurface(item, Modifier.weight(1f).then(itemModifier)) {
                    itemContent(it)
                }
            }
            if (itemChunk.size < columnCount) {
                LazyGridViewItemSurface(null, Modifier.weight(1f).then(itemModifier)) {}
            }
        }
    }
}

@Composable
private fun <T> LazyGridViewItemSurface(item: T?, modifier: Modifier, content: @Composable (T) -> Unit) {
    Surface(modifier = modifier) {
        item?.let { content(it) }
    }
}