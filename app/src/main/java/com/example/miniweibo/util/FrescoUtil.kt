package com.example.miniweibo.util


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.facebook.binaryresource.FileBinaryResource
import com.facebook.common.executors.UiThreadImmediateExecutorService
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.core.ImagePipelineFactory
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequest.RequestLevel
import com.facebook.imagepipeline.request.ImageRequestBuilder
import javax.inject.Inject


object FrescoUtil {
    private val TAG = "FrescoDownloadUtil"

    fun downloadPic(
        url: String,
        callback: (Bitmap) -> Unit = {},
        width: Int = 16,
        height: Int = 16
    ) {
        Log.d(TAG, "run：$url")
        val uri = Uri.parse(url)
        val request: ImageRequest = ImageRequestBuilder
            .newBuilderWithSource(uri)
            .setAutoRotateEnabled(true)
            .setLocalThumbnailPreviewsEnabled(true)
            .setLowestPermittedRequestLevel(RequestLevel.FULL_FETCH)
            .setProgressiveRenderingEnabled(false)
            .setResizeOptions(ResizeOptions(width, height))
            .build()

        val imagePipeline = Fresco.getImagePipeline();
        val dataSource = imagePipeline.fetchDecodedImage(request, this);
        dataSource.subscribe(object : BaseBitmapDataSubscriber() {
            override fun onNewResultImpl(bitmap: Bitmap?) {
                if (bitmap != null) {
                    callback(bitmap)
                }
                Log.d(TAG, "下载成功 $url")
            }

            override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>) {

            }
        }, UiThreadImmediateExecutorService.getInstance())
    }


    fun getGitFile(gifUrl: String, callback: (Bitmap) -> Unit) {
        Log.d(TAG, "emotionUrl:$gifUrl")
        val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(gifUrl))
            .setLowestPermittedRequestLevel(RequestLevel.DISK_CACHE)
            .build()
        val cacheKey = DefaultCacheKeyFactory.getInstance()
            .getEncodedCacheKey(request, null)
        val resource = ImagePipelineFactory.getInstance()
            .mainFileCache.getResource(cacheKey)
        if (resource != null) {
            val file = (resource as FileBinaryResource).file
            callback(BitmapFactory.decodeFile(file.absolutePath))
        } else {
            downloadPic(gifUrl, callback)
        }

    }


}