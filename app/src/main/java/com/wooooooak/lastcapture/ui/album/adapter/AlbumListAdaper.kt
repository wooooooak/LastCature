package com.wooooooak.lastcapture.ui.album.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wooooooak.lastcapture.MyApplication
import com.wooooooak.lastcapture.data.model.Album
import com.wooooooak.lastcapture.databinding.ItemAlbumBinding

class AlbumListAdapter : ListAdapter<Album, AlbumListAdapter.ViewHolder>(AlbumDiffCallback()) {

    private val pref = MyApplication.pref

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = getItem(position)
        album.isSelected = album.thumbnailUri.toString() in pref.selectedThumbnailUris
        holder.bind(album)
    }

    private fun createOnClickListener(binding: ItemAlbumBinding, _album: Album) = View.OnClickListener {
        if (_album.thumbnailUri.toString() in pref.selectedThumbnailUris) {
            pref.applyFolder { remove(_album.thumbnailUri.toString()) }
        } else {
            pref.applyFolder { add(_album.thumbnailUri.toString()) }
        }
        with(binding) {
            _album.isSelected = !_album.isSelected
            album = _album
            executePendingBindings()
        }
    }

    inner class ViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(_album: Album) {
            binding.apply {
                album = _album
                onClickListener = createOnClickListener(binding, _album)
                executePendingBindings()
            }
        }
    }
}

private class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album) = oldItem.name == newItem.name
    override fun areContentsTheSame(oldItem: Album, newItem: Album) = oldItem == newItem
}