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
    private var lastVideoIndex: Int = -1

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == SCROLL_STATE_IDLE) {
            val firstIndex = layoutManager.findFirstVisibleItemPosition()
            val lastIndex = layoutManager.findLastVisibleItemPosition()
            if (firstIndex < 0 || lastIndex < 0) {
                return
            }
            val randomIndex = getRandomIndex(firstIndex, lastIndex)
            Log.d(TAG, "随机位置为：$randomIndex")
            val view = layoutManager.findViewByPosition(randomIndex)
            if (view != null) {
                this.lastVideoIndex = randomIndex
                val viewHolder = recyclerView.getChildViewHolder(view) as VideoViewHolder
                viewHolder.playVideo()
                Log.d(TAG, System.currentTimeMillis().toString())
            } else {
                Log.d(TAG, "位置为空：$randomIndex")
            }
//            randomIndex = getRandomIndex(firstIndex, lastIndex)
//            while (randomIndex == lastIndex) {
//                randomIndex = getRandomIndex(firstIndex, lastIndex)
//            }
//            view = layoutManager.findViewByPosition(randomIndex)
//            if (view != null) {
//                this.lastVideoIndex = randomIndex
//                val viewHolder = recyclerView.getChildViewHolder(view) as VideoViewHolder
//                viewHolder.playSweep()
//                Log.d(TAG, System.currentTimeMillis().toString())
//            } else {
//                Log.d(TAG, "位置为空：$randomIndex")
//            }
        }
    }

    fun resetImg(recyclerView: RecyclerView) {
        if (lastVideoIndex != -1) {
            val view = layoutManager.findViewByPosition(this.lastVideoIndex)
            if (view != null) {
                val viewHolder = recyclerView.getChildViewHolder(view) as VideoViewHolder
                viewHolder.imgVisible()
            }
        }
    }

    private fun getRandomIndex(start: Int, end: Int): Int {
        return ((start + 2) until end - 1).random()
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (lastVideoIndex != -1) {
            val view = layoutManager.findViewByPosition(this.lastVideoIndex)
            if (view != null) {
                val viewHolder = recyclerView.getChildViewHolder(view) as VideoViewHolder
                viewHolder.imgVisible()
            }
        }
        mediaPlayer.reset()
    }
}