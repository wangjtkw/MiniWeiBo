package com.example.miniweibo.ui.video

import android.media.MediaPlayer
import android.util.Log
import android.view.Surface
import android.view.View
import com.example.miniweibo.common.DataBindingViewHolder
import com.example.miniweibo.data.bean.entity.ImgEntity
import com.example.miniweibo.databinding.RvItemVideoBinding
import com.example.miniweibo.util.ToastUtil

class VideoViewHolder(view: View, private val mediaPlayer: MediaPlayer) :
    DataBindingViewHolder<ImgEntity>(view) {
    private val TAG = "VideoViewHolder"
    private val mBinding: RvItemVideoBinding = viewHolderBinding(view)
    private var mData: ImgEntity? = null

    override fun bindData(data: ImgEntity, position: Int) {
        mData = data
        mBinding.imgEntity = data
//        mBinding.videoImgSweep.setImgUrl(
//            data.url
//        )
//        mBinding.videoImgSweep.setImgListResource(
//            listOf(
//                R.drawable.img_1,
//                R.drawable.img_2,
//                R.drawable.img_3,
//                R.drawable.img_4,
//            )
//        )
//        mBinding.videoImgSweep.setImgListResource()

    }

    fun imgVisible() {
        mBinding.videoImg.visibility = View.VISIBLE
    }

    private fun imgInvisible() {
        mBinding.videoImg.visibility = View.INVISIBLE
    }


    fun playVideo() {
        mBinding.videoPlayerView.getMySurfaceTexture()?.let {
            mediaPlayer.setSurface(Surface(it))
        } ?: Log.d(TAG, "surface is null")

        mediaPlayer.reset()
        mediaPlayer.setDataSource(mData!!.videoUrl)
        mediaPlayer.prepareAsync()
        ToastUtil.makeToast("视频加载中...")
        mediaPlayer.setOnPreparedListener {
            imgInvisible()
            mediaPlayer.start()
            ToastUtil.makeToast("视频加载完成")
            Log.d(TAG, "on start")
        }
    }

    fun resetVideo() {
        imgVisible()
        imgVisible()
        mediaPlayer.reset()
    }

    fun releaseSweep() {
        mBinding.videoImgSweep.releaseBitmap()
    }

    fun playSweep() {
        mBinding.videoImgSweep.prepareBitmap()
        imgInvisible()
        mBinding.videoImgSweep.startShow()

    }
}