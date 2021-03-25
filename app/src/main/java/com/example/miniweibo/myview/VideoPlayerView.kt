package com.example.miniweibo.myview

import android.content.Context
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.util.AttributeSet
import android.util.Log
import android.view.TextureView

class VideoPlayerView : TextureView, TextureView.SurfaceTextureListener {
    private val TAG = "VideoPlayerView"

    private var mMediaPlayer: MediaPlayer? = null

    private var mySurfaceTexture: SurfaceTexture? = null

    private var mySurfaceTextureListener: SurfaceTextureListener? = null

    init {
        surfaceTextureListener = this
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    fun setMySurfaceTextureListener(surfaceTextureListener: SurfaceTextureListener) {
        mySurfaceTextureListener = surfaceTextureListener
    }

    fun getMySurfaceTexture(): SurfaceTexture? {
        return mySurfaceTexture
    }

    fun checkMedia() {
        if (mMediaPlayer == null) {
            throw Exception("should set mediaPlayer")
        }
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        mySurfaceTextureListener?.onSurfaceTextureAvailable(surface, width, height)
        this.mySurfaceTexture = surface
        if (mySurfaceTextureListener == null) {
            Log.d(TAG, "mySurfaceTextureListener is null")
        }
        Log.d(TAG, "on available")
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
        mySurfaceTextureListener?.onSurfaceTextureSizeChanged(surface, width, height)
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        mySurfaceTextureListener?.onSurfaceTextureDestroyed(surface)
        Log.d(TAG, "on destroyed")
        return true
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
        mySurfaceTextureListener?.onSurfaceTextureUpdated(surface)
    }


}