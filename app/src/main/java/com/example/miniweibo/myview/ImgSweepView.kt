package com.example.miniweibo.myview

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.facebook.common.executors.CallerThreadExecutor
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequestBuilder
import java.util.*
import kotlin.collections.ArrayList

class ImgSweepView : View {
    private val TAG = "ImgSweepView"

    //图片数组
    private var mDrawableList: ArrayList<Drawable>? = null

    private var mBitmapList: ArrayList<Bitmap>? = null

    private var mPaint: Paint? = null

    //是否可以开始动画
    private var isReadyToShow: Boolean = false

    //动画进程
    private var mProgress = 0.0f

    //值动画，控制进程
    private var mValueAnimator: ValueAnimator? = null

    //默认动画持续时间
    private var mDuration: Long = 500L

    //矩阵，初始化图片大小
    private var mMatrix: Matrix? = null

    // 当前位置
    private var mIndex = -1

    //图片缩放倍数
    private var imgScale = 1f

    private var mRectF: RectF? = null

    private var mHandler: Handler? = null

    private var mTimer: Timer? = null
    private var mTimerTask: TimerTask? = null

    private var isCancel = true

    private var mRect: Rect? = null

    private var isShowing = false


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
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val mWidth = measureSize(widthMeasureSpec, 100)
        setMeasuredDimension(mWidth, mWidth)
    }

    private fun measureSize(spec: Int, initialSize: Int): Int {
        val specSize = MeasureSpec.getSize(spec)
        var result: Int = initialSize
        when (MeasureSpec.getMode(spec)) {
            MeasureSpec.AT_MOST, MeasureSpec.EXACTLY -> {
                result = specSize
            }
            MeasureSpec.UNSPECIFIED -> {
                result = initialSize
            }
        }
        return result
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mBitmapList.isNullOrEmpty()) {
            return
        }
        if (canvas == null) {
            return
        }
        val lastIndex = getLastIndex()
        val lastBitmap = if (mIndex == -1) {
            mBitmapList!![1]
        } else {
            mBitmapList!![lastIndex]
        }
        calculateBitmapScale(lastBitmap)
        mMatrix!!.setScale(imgScale, imgScale)
        canvas.drawBitmap(lastBitmap, mMatrix!!, mPaint)
//        lastDrawable.bounds = mRect!!
//        lastDrawable.draw(canvas)
        canvas.saveLayer(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)

        mRectF!!.set(
            right.toFloat() - right.toFloat() * (1 - mProgress),
            top.toFloat(),
            right.toFloat(),
            bottom.toFloat()
        )
        mPaint!!.reset()

        val currentBitmap = if (mIndex == -1) {
            mBitmapList!![0]
        } else {
            mBitmapList!![mIndex]
        }
        calculateBitmapScale(currentBitmap)
        mMatrix!!.setScale(imgScale, imgScale)
        canvas.clipRect(mRectF!!)

//        mRect!!.set((right - right * (1 - mProgress)).toInt(), top, right, bottom)
//        currentDrawable.draw(canvas)
        canvas.drawBitmap(currentBitmap, mMatrix!!, mPaint)
        canvas.restore()
        mPaint!!.reset()
    }

    fun prepareBitmap() {
        if (mDrawableList.isNullOrEmpty()) {
            return
        }
        mDrawableList!!.map {
            mBitmapList!!.add(drawableToBitMap(it))
        }
    }

//    fun getDrawableImage(drawable: Drawable): Drawable {
//        val mBitmap = drawableToBitMap(drawable)
//        return bitmapToDrawable(mBitmap)
//    }

    private fun bitmapToDrawable(bitmap: Bitmap): Drawable? {
        if (bitmap.width <= 0 || bitmap.height <= 0) {
            return null
        }
        val mMatrix = Matrix()
        calculateBitmapScale(bitmap)
        mMatrix.postScale(imgScale, imgScale)
        val mWidth = if (bitmap.width > 0) {
            bitmap.width
        } else {
            1
        }
        val mHeight = if (bitmap.height > 0) {
            bitmap.height
        } else {
            1
        }
        Log.d(TAG,"")
        Log.d(TAG, "=====>bitmap:width:$width height:$height")
        val mBitmap = bitmap.copy(bitmap.config,true)
        if (mBitmap.width <= 0 || mBitmap.height <= 0) {
            return null
        }
        val newBitMap: Bitmap =
            Bitmap.createBitmap(
                mBitmap,
                0,
                0,
                mWidth,
                mHeight,
                mMatrix,
                true)
        Log.d(TAG, "<=======bitmap:width:$width height:$height")
        return BitmapDrawable(resources, newBitMap)
    }

    private fun drawableToBitMap(drawable: Drawable): Bitmap {
        //获取宽度、高度
        val width: Int = drawable.intrinsicWidth
        val height: Int = drawable.intrinsicHeight

        //获取颜色格式
        val config: Bitmap.Config =
            if (drawable.opacity != PixelFormat.OPAQUE) {
                Bitmap.Config.ARGB_8888
            } else {
                Bitmap.Config.RGB_565
            }
        //创建BitMap流
        val bitmap: Bitmap = Bitmap.createBitmap(width, height, config)
        val canvas = Canvas(bitmap) //绘制
        drawable.setBounds(0, 0, width, height)
        drawable.draw(canvas)
        return bitmap
    }


    private fun calculateBitmapScale(bitmap: Bitmap) {
        val bitmapWidth = bitmap.width.toFloat()
        val bitmapHeight = bitmap.height.toFloat()
        val bitmapRatio = bitmapWidth / bitmapHeight
        imgScale = if (bitmapRatio > 1) {
            //宽图
            height / bitmapHeight
        } else {
            //长图
            width / bitmapWidth
        }
    }

    /**
     * 计算图片缩放比例
     */
    private fun calculateDrawableScale(drawable: Drawable) {
        val bitmapWidth = drawable.intrinsicWidth.toFloat()
        val bitmapHeight = drawable.intrinsicHeight.toFloat()
        val bitmapRatio = bitmapWidth / bitmapHeight
        imgScale = if (bitmapRatio > 1) {
            //宽图
            height / bitmapHeight
        } else {
            //长图
            width / bitmapWidth
        }
    }

    private fun getLastIndex(): Int {
        return if (mIndex + 1 < mDrawableList!!.size) {
            mIndex + 1
        } else {
            0
        }
    }

    private fun init() {
        mDrawableList = ArrayList()
        mBitmapList = ArrayList()
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mMatrix = Matrix()
        mRectF = RectF()
        mRect = Rect()
        mHandler = Handler(Looper.myLooper()!!)
        initAnimator()
        initTimer()
    }

    private fun initTimer() {
        mTimer = Timer()
        mTimerTask = object : TimerTask() {
            override fun run() {

            }
        }
    }

    private fun initAnimator() {
        mValueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
        mValueAnimator!!.duration = mDuration
        mValueAnimator!!.interpolator = LinearInterpolator()
        mValueAnimator!!.addUpdateListener {
            val value = it.animatedValue as Float
            setProcess(value)
        }
        mValueAnimator!!.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                startAnimation()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
    }

    private fun setProcess(progress: Float) {
        require(progress in 0.0f..1.0f) { "progress must in 0.0 to 1.0, but is $progress" }
        mProgress = progress
        invalidate()
    }

    fun setDuration(duration: Long) {
        mDuration = duration
    }

//    fun setImgListBitmap(bitmapList: List<Bitmap>) {
//        if (bitmapList.isNullOrEmpty()) {
//            return
//        }
//        bitmapList
//        BitmapDrawable(resources, newBitMap)
//        mDrawableList!!.addAll(bitmapList)
//        isReadyToShow = true
//    }

    fun setImgListResource(resourceList: List<Int>) {
        if (resourceList.isNullOrEmpty()) {
            return
        }
        resourceList.map {
            mDrawableList!!.add(ContextCompat.getDrawable(context, it)!!)
        }
    }

    private fun startAnimation() {
        if (!isCancel && !isShowing) {
            mHandler!!.postDelayed({
                mValueAnimator!!.start()
                mIndex = getLastIndex()
            }, 1000)
        }
    }

    fun startShow() {
        if (!isShowing) {
            isCancel = false
            startAnimation()
        }

    }

    fun finishShow() {
        isShowing = false
        isCancel = true
    }

    fun addImg(bitmap: Bitmap) {
        val drawable = bitmapToDrawable(bitmap)
        if (drawable != null) {
            mDrawableList!!.add(drawable)
        }
    }

    fun setImgListUrl(urlList: List<String>) {
        if (urlList.isNullOrEmpty()) {
            return
        }
        urlList.map {
            loadImg(it)
        }
    }

    fun setImgUrl(url: String) {
        if (url.isEmpty()) {
            return
        }
        loadImg(url)
    }

    private fun loadImg(url: String) {
        val imageRequest = ImageRequestBuilder
            .newBuilderWithSource(
                Uri.parse(url)
            )
            .setProgressiveRenderingEnabled(true)
            .build()
        val imagePipeline = Fresco.getImagePipeline()
        val dataSource =
            imagePipeline.fetchDecodedImage(imageRequest, context)
        dataSource.subscribe(
            object : BaseBitmapDataSubscriber() {
                override fun onNewResultImpl(bitmap: Bitmap?) {
                    if (bitmap != null && bitmap.width > 0 && bitmap.height > 0) {
//                        Log.d(TAG, "bitmap:width:$width height:$height")
                        addImg(bitmap)
                    }
                }

                override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>) {
                    Log.e(TAG, dataSource.failureCause?.message ?: "unknown error")
                }
            }, CallerThreadExecutor.getInstance()
        )
    }

    fun releaseBitmap() {
        isShowing = false
        mBitmapList!!.map {
            it.recycle()
        }
        mBitmapList!!.clear()
    }

}