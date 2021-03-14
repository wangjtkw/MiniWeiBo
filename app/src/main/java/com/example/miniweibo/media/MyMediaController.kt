package com.example.miniweibo.media

import android.media.MediaPlayer
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.example.miniweibo.ui.video.VideoViewHolder

class MyMediaController(
    private val layoutManager: GridLayoutManager,
    private val mediaPlayer: MediaPlayer
) : RecyclerView.OnScrollListener() {

    private val TAG = "MediaController"
    private var lastIndex: Int = -1

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == SCROLL_STATE_IDLE) {
            val firstIndex = layoutManager.findFirstVisibleItemPosition()
            val lastIndex = layoutManager.findLastVisibleItemPosition()
            val randomIndex = ((firstIndex + 2) until lastIndex - 1).random()
            Log.d(TAG, "随机位置为：$randomIndex")
            val view = layoutManager.findViewByPosition(randomIndex)
            if (view != null) {
                this.lastIndex = randomIndex
                val viewHolder = recyclerView.getChildViewHolder(view) as VideoViewHolder
                viewHolder.play()
                Log.d(TAG, System.currentTimeMillis().toString())
            } else {
                Log.d(TAG, "位置为空：$randomIndex")
            }
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (lastIndex != -1) {
            val view = layoutManager.findViewByPosition(this.lastIndex)
            if (view != null) {
                val viewHolder = recyclerView.getChildViewHolder(view) as VideoViewHolder
                viewHolder.imgVisible()
            }
        }
        mediaPlayer.reset()

    }
}