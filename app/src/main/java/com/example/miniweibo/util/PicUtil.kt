package com.example.miniweibo.util


import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


object PicUtil {
    private val TAG = "PicUtil"

    fun preloadPic(
        context: Context,
        url: String,
        width: Int = 48,
        height: Int = 48
    ) {
        Glide.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .preload(width, height)
    }

    fun loadPic(
        context: Context,
        url: String,
        width: Int = 48,
        height: Int = 48
    ): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            bitmap = Glide.with(context)
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .submit(width, height)
                .get()
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
        }
        return bitmap
    }
}