package com.wooooooak.lastcapture.ui.album.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wooooooak.lastcapture.MyApplication
import com.wooooooak.lastcapture.R
import com.wooooooak.lastcapture.adapter.setBackgroundResource
import com.wooooooak.lastcapture.data.Album
import com.wooooooak.lastcapture.databinding.ItemAlbumBinding
import kotlinx.android.synthetic.main.item_album.view.*

class AlbumListAdapter : ListAdapter<Album, AlbumListAdapter.ViewHolder>(AlbumDiffCallback()) {
    private val pref = MyApplication.pref
    private val selectedAlbumList = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = getItem(position)
        if (pref.selectedFolderUris.contains(album.albumUriPath)) {
            selectedAlbumList.add(position)
            album.isSelected = true
        } else {
            selectedAlbumList.remove(position)
            album.isSelected = false
        }
        holder.bind(album, createOnClickListener(album, position))
    }

    private fun createOnClickListener(album: Album, position: Int): View.OnClickListener {
        return View.OnClickListener {
            if (selectedAlbumList.contains(position)) {
                pref.applyFolder { remove(album.albumUriPath) }
                it.setBackgroundResource(0)
                selectedAlbumList.remove(position)
                album.isSelected = false
            } else {
                pref.applyFolder { add(album.albumUriPath) }
                it.setBackgroundResource(R.drawable.border_red)
                selectedAlbumList.add(position)
                album.isSelected = true
            }
        }
    }

    inner class ViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album, listener: View.OnClickListener) {
            binding.apply {
                this.album = album
                onClickListener = listener
                setBackgroundResource(binding.root.imageView, album.isSelected)
            }
        }
    }
}

private class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album) = oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Album, newItem: Album) = oldItem == newItem
}