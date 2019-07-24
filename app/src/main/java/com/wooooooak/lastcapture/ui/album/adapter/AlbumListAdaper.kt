package com.wooooooak.lastcapture.ui.album.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wooooooak.lastcapture.R
import com.wooooooak.lastcapture.data.Album
import com.wooooooak.lastcapture.databinding.ItemAlbumBinding

class AlbumListAdapter : ListAdapter<Album, AlbumListAdapter.ViewHolder>(AlbumDiffCallback()) {

    val selectedAlbumList = mutableListOf<Int>()

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
        holder.bind(album, createOnClickListener(album, position))
    }

    private fun createOnClickListener(album: Album, position: Int): View.OnClickListener {
        return View.OnClickListener {
            if (selectedAlbumList.contains(position)) {
                it.setBackgroundResource(0)
                selectedAlbumList.remove(position)
                album.isSelected = false
            } else {
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
            }
        }
    }
}

private class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album) = oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Album, newItem: Album) = oldItem == newItem
}