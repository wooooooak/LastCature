package com.wooooooak.lastcapture.ui.screenshots.adater

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wooooooak.lastcapture.R
import com.wooooooak.lastcapture.databinding.ItemThumbnailBinding
import com.wooooooak.lastcapture.ui.screenshots.DetailScreenShotActivity
import java.io.File

class ScreenShotAdapter(private val activity: Activity) :
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
            bind(createOnClickListener(screenShot), screenShot)
            itemView.tag = screenShot
        }
    }

    private fun createOnClickListener(file: File): View.OnClickListener {
        return View.OnClickListener {
            val intent = Intent(activity, DetailScreenShotActivity::class.java).apply {
                putExtra(DetailScreenShotActivity.SHARED_FILE_PATH, file.path)
            }
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, it.findViewById(R.id.thumbnail), "screenShot"
            )
            activity.startActivity(intent, options.toBundle())
        }
    }

    class ViewHolder(
        val binding: ItemThumbnailBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listener: View.OnClickListener, item: File) {
            binding.apply {
                screenShot = item
                onClickListener = listener
            }
        }
    }
}

private class ScreenShotDiffCallback : DiffUtil.ItemCallback<File>() {
    override fun areItemsTheSame(oldItem: File, newItem: File) = oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: File, newItem: File) = oldItem == newItem
}