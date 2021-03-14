package com.example.miniweibo.ui.video

import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.miniweibo.R
import com.example.miniweibo.data.bean.entity.ImgEntity
import okhttp3.internal.notify

class VideoAdapter(private val mediaPlayer: MediaPlayer) : RecyclerView.Adapter<VideoViewHolder>() {
    private val TAG = "VideoAdapter"

    private val dataList = ArrayList<ImgEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = inflateView(parent, R.layout.rv_item_video)
        return VideoViewHolder(view, mediaPlayer)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val data = getItem(position)
        data.let {
            holder.bindData(it, position)
        }
    }

    override fun onViewRecycled(holder: VideoViewHolder) {
        super.onViewRecycled(holder)
        Log.d(TAG, System.currentTimeMillis().toString())
        holder.reset()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun addDataList(list: List<ImgEntity>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    private fun inflateView(viewGroup: ViewGroup, @LayoutRes viewType: Int): View {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return layoutInflater.inflate(viewType, viewGroup, false)
    }

    private fun getItem(position: Int): ImgEntity {
        return dataList[position]
    }
}