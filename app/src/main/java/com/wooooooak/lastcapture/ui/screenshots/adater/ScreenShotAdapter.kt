package com.wooooooak.lastcapture.ui.screenshots.adater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.wooooooak.lastcapture.databinding.ItemThumbnailBinding
import java.io.File

class ScreenShotAdapter :
    ListAdapter<File, ScreenShotAdapter.ViewHolder>(ScreenShotDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemThumbnailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val screenShot = getItem(position)
        holder.apply {
            bind(createOnClickListener(), screenShot)
            itemView.tag = screenShot
        }
    }


    class ViewHolder(
        private val binding: ItemThumbnailBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: File) {
            binding.apply {
                screenShot = item
                onClickListener = listener
            }
        }
    }

    private fun createOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            Snackbar.make(it, "클릭!", Snackbar.LENGTH_SHORT).show()
        }
    }
}

private class ScreenShotDiffCallback : DiffUtil.ItemCallback<File>() {
    override fun areItemsTheSame(oldItem: File, newItem: File) = oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: File, newItem: File) = oldItem == newItem
}