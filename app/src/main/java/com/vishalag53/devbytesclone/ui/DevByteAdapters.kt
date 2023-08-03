package com.vishalag53.devbytesclone.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vishalag53.devbytesclone.R
import com.vishalag53.devbytesclone.databinding.DevbyteItemBinding
import com.vishalag53.devbytesclone.domain.Video

class DevByteAdapter(val callback: VideoClick) : RecyclerView.Adapter<DevByteAdapter.DevByteViewHolder>() {
    var videos: List<Video> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevByteViewHolder {
        val withDataBinding: DevbyteItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            DevByteViewHolder.LAYOUT,
            parent,
            false
        )
        return DevByteViewHolder(withDataBinding)
    }

    override fun getItemCount() = videos.size

    override fun onBindViewHolder(holder: DevByteViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.video = videos[position]
            it.videoCallback = callback
        }
    }


    class DevByteViewHolder(val viewDataBinding: DevbyteItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.devbyte_item
        }
    }

    class VideoClick(val block: (Video) -> Unit) {
        fun onClick(video: Video) = block(video)
    }
}