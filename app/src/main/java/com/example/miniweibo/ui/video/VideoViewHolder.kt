package com.example.miniweibo.ui.video

import android.media.MediaPlayer
import android.util.Log
import android.view.Surface
import android.view.View
import com.example.miniweibo.common.DataBindingViewHolder
import com.example.miniweibo.data.bean.entity.ImgEntity
import com.example.miniweibo.databinding.RvItemVideoBinding

class VideoViewHolder(view: View, private val mediaPlayer: MediaPlayer) :
    DataBindingViewHolder<ImgEntity>(view) {
    private val TAG = "VideoViewHolder"
    private val mBinding: RvItemVideoBinding = viewHolderBinding(view)
    private var mData: ImgEntity? = null

    override fun bindData(data: ImgEntity, position: Int) {
        mData = data
        mBinding.imgEntity = data
    }

    fun imgVisible() {
        mBinding.videoImg.visibility = View.VISIBLE
    }

    fun imgInvisible() {
        mBinding.videoImg.visibility = View.INVISIBLE
    }


    fun play() {
        mBinding.videoPlayerView.getMySurfaceTexture()?.let {
            mediaPlayer.setSurface(Surface(it))
        }?:Log.d(TAG,"surface is null")

        mediaPlayer.reset()
        mediaPlayer.setDataSource(mData!!.videoUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            imgInvisible()
            mediaPlayer.start()
            Log.d(TAG, "on start")
        }


    }

    fun reset() {
        imgVisible()
        mediaPlayer.reset()
    }
}