package com.wooooooak.lastcapture.ui.screenshots.adater

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.wooooooak.lastcapture.R
import com.wooooooak.lastcapture.databinding.ItemThumbnailBinding
import com.wooooooak.lastcapture.ui.screenshots.DetailScreenShotActivity
import com.wooooooak.lastcapture.ui.screenshots.ImageViewerViewModel
import com.wooooooak.lastcapture.utilities.lastModifiedTime
import wooooooak.dev.kcsimplealertview.woakalertview.SimpleAlertView
import java.io.File
import java.nio.file.Files

class ScreenShotAdapter(
    private val activity: Activity,
    private val imageViewerViewModel: ImageViewerViewModel
) : ListAdapter<File, ScreenShotAdapter.ViewHolder>(ScreenShotDiffCallback()) {

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
            val (onClickListener, onLongClickListener) = createOnClickListener(screenShot)
            bind(onClickListener, onLongClickListener, screenShot)
            itemView.tag = screenShot
        }
    }

    private fun createOnClickListener(file: File): Pair<View.OnClickListener, View.OnLongClickListener> {
        val onClickListener = View.OnClickListener {
            val intent = Intent(activity, DetailScreenShotActivity::class.java).apply {
                putExtra(DetailScreenShotActivity.SHARED_FILE_PATH, file.path)
            }
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, it.findViewById(R.id.thumbnail), "screenShot"
            )
            activity.startActivity(intent, options.toBundle())
        }

        val onLongClickListener = View.OnLongClickListener {
            showDeleteAlertView(file, it)
            true
        }

        return Pair(onClickListener, onLongClickListener)
    }

    private fun showDeleteAlertView(file: File, view: View) {
        SimpleAlertView(activity as AppCompatActivity) {
            title {
                text = "해당 이미지를\n삭제하시겠습니까?"
            }
            button {
                text = "취소"
            }
            button {
                text = "삭제"
                textColor = ContextCompat.getColor(activity, R.color.colorAccent)
                onClick = {
                    if (file.exists()) {
                        val isDeletedSuccessfully = file.delete()
                        if (isDeletedSuccessfully) {
                            imageViewerViewModel.refreshItem()
                            activity.baseContext.sendBroadcast(
                                Intent(
                                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)
                                )
                            )
                            Snackbar.make(view, "삭제 완료", Snackbar.LENGTH_SHORT).show()
                        }
                    } else {
                        Snackbar.make(view, "존재하지 않는 파일입니다.", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }.show()
    }


    inner class ViewHolder(
        val binding: ItemThumbnailBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            onClickListener: View.OnClickListener,
            onLongClickListener: View.OnLongClickListener,
            item: File
        ) {
            binding.apply {
                screenShot = item
                this.onClickListener = onClickListener
                this.onLongClickListener = onLongClickListener
                time = item.lastModifiedTime
            }
        }
    }
}

private class ScreenShotDiffCallback : DiffUtil.ItemCallback<File>() {
    override fun areItemsTheSame(oldItem: File, newItem: File) = oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: File, newItem: File) = oldItem == newItem
}