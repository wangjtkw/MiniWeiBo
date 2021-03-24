package com.example.miniweibo.myview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import android.view.WindowManager
import androidx.annotation.DrawableRes
import com.facebook.common.executors.CallerThreadExecutor
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequestBuilder
import kotlin.math.abs
import kotlin.math.sqrt


class ImgViewer : View {
    private val TAG = "ImgViewer"

    //bitmap 所有格式将转为bitmap 进行绘制
    private var mBitmap: Bitmap? = null

    private var mPaint: Paint? = null

    //与viewpager进行配合，来解决滑动冲突
    private var mOnMoveListener: OnChildMovingListener? = null

    //是否正在缩放，用来区分位移和缩放的行为
    private var isScaling = false

    //缩放比例
    private var imgScale = 1f

    //屏幕长宽
    private var totalWidth = 0
    private var totalHeight = 0

    //缩放显示后图片宽高
    private var mBitmapWidth = 0f
    private var mBitmapHeight = 0f

    //bitmap 位置矩形
    private var mBitmapRect: RectF? = null

    //是否是长图
    private var isLongChart = false

    //双指缩放中心
    private var centerX = 0f
    private var centerY = 0f

    //上次刷新位置
    private var lastIndexX = 0f
    private var lastIndexY = 0f

    //最小移动触发距离
    private var minDistance = 10

    //上次两指间的距离
    private var lastDistance = 0f

    //两指触碰第一次点击，用来初始化两指间距用
    private var isTwoFingerFirstMove = true

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

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) {
            return
        }
        if (mBitmap == null) {
            return
        }
        if (isScaling) {
            mBitmapRect!!.run {
                left = centerX - (centerX - left) * imgScale
                right = centerX - (centerX - right) * imgScale
                top = centerY - (centerY - top) * imgScale
                bottom = centerY - (centerY - bottom) * imgScale
            }
            imgScale = 1f
        }
        canvas.save()
        canvas.drawBitmap(
            mBitmap!!, null, mBitmapRect!!, mPaint
        )
        canvas.restore()
    }

    /**
     * 通过资源文件进行设置
     * @param resId:资源文件id
     */
    fun setImg(@DrawableRes resId: Int) {
        val bitmap = BitmapFactory.decodeResource(resources, resId)
        setImg(bitmap)
    }

    /**
     * 通过bitmap进行设置
     */
    fun setImg(bitmap: Bitmap?) {
        if (bitmap != null) {
            mBitmap = bitmap
            calculateScale()
            setPosition()
            invalidate()
        }
    }

    /**
     * 初始化bitmap缩放后的位置
     */
    private fun setPosition() {
        val left = (totalWidth - mBitmapWidth) / 2
        val right = left + mBitmapWidth
        val top = (totalHeight - mBitmapHeight) / 2
        val bottom = top + mBitmapHeight
        mBitmapRect = RectF(left, top, right, bottom)
    }

    /**
     * 通过链接进行设置，内部使用fresco进行加载，通常情况下会使用缓存加载
     * @param url：图片链接
     */
    fun setImg(url: String?) {
        if (url.isNullOrEmpty()) {
            return
        }
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
                    if (bitmap != null) {
                        setImg(bitmap)
                    }
                }

                override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>) {
                    Log.e(TAG, dataSource.failureCause?.message ?: "unknown error")
                }
            }, CallerThreadExecutor.getInstance()
        )
    }

    /**
     * 设置图片缩放比例
     * @param scale：缩放比例
     */
    private fun setImgScale(scale: Float) {
        imgScale = if (scale > 1.0) {
            (1 + scale / 10)

        } else {
            (1 - (1 / scale) / 10)
        }
        invalidate()
    }

    /**
     * 设置父viewGroup监听，以此来解决滑动冲突问题
     */
    fun setOnMovingListener(onMoveListener: OnChildMovingListener) {
        mOnMoveListener = onMoveListener
    }

    private fun init() {
        getScreenSize()
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        centerX = totalWidth / 2f
        centerY = totalHeight / 2f
        Log.d(TAG, "centerX:$centerX centerY:$centerY")
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return true
        }
        val mX = event.x
        val mY = event.y
        when (event.action) {
            ACTION_DOWN -> {
                isTwoFingerFirstMove = true
                if (event.pointerCount == 1) {
                    lastIndexX = event.x
                    lastIndexY = event.y
                }
            }
            ACTION_MOVE -> {
                if (event.pointerCount == 1) {
                    singleFinger(mX, mY)
                } else if (event.pointerCount == 2) {
                    twoFinger(mX, mY, event)
                }
            }
            ACTION_UP -> {
                isScaling = false
            }
        }
        return true
    }

    /**
     * 初始化图片时计算缩放比例用
     */
    private fun calculateScale() {
        val bitmapWidth = mBitmap!!.width.toFloat()
        val bitmapHeight = mBitmap!!.height.toFloat()
        val bitmapRatio = bitmapWidth / bitmapHeight
        val screenRatio = totalWidth / totalHeight.toFloat()

        if (bitmapRatio > screenRatio) {
            //宽图
            isLongChart = false
            imgScale = totalWidth / bitmapWidth
            mBitmapWidth = totalWidth.toFloat()
            mBitmapHeight = bitmapHeight * imgScale

        } else {
            //长图
            isLongChart = true
            imgScale = totalHeight / bitmapHeight
            mBitmapWidth = bitmapWidth * imgScale
            mBitmapHeight = totalHeight.toFloat()
        }
    }

    /**
     * 计算两指缩放
     */
    private fun twoFinger(xIndex: Float, yIndex: Float, event: MotionEvent) {
        isScaling = true
        val x1 = event.getX(0)
        val y1 = event.getY(0)
        val x2 = event.getX(1)
        val y2 = event.getY(1)
        val distance = calculateDistance(x1, y1, x2, y2)
        if (isTwoFingerFirstMove) {
            lastDistance = distance
            isTwoFingerFirstMove = false
        }
        val distanceDiffer = abs(lastDistance - distance).toInt()

        Log.d(TAG, "distanceDiffer:$distanceDiffer")

        if (isScaling && isTouchBitmap(xIndex, yIndex) && distanceDiffer > minDistance) {
            setImgScale(distance / lastDistance)
            centerX = (x1 + x2) / 2
            centerY = (y1 + y2) / 2
            Log.d(TAG, "centerX:$centerX centerY:$centerY")
            invalidate()
        }
        lastDistance = distance
    }

    /**
     * 计算单指移动
     */
    private fun singleFinger(xIndex: Float, yIndex: Float) {
        isScaling = false
        val xTemp = (xIndex - lastIndexX)
        val yTemp = (yIndex - lastIndexY)
        val distance =
            sqrt((xTemp * xTemp + yTemp * yTemp).toDouble())
        if (!isScaling && isTouchBitmap(xIndex, yIndex) && distance > minDistance) {

            mBitmapRect!!.run {
                if (left <= 0 && right >= totalWidth) {
                    if (left + xTemp <= 0 && right + xTemp >= totalWidth) {
                        left += xTemp
                        right += xTemp
                        if (mOnMoveListener != null) {
                            Log.d(TAG, "stop run")
                            mOnMoveListener!!.startDrag()
                        }
                    } else {
                        if (mOnMoveListener != null) {
                            Log.d(TAG, "stop run")
                            mOnMoveListener!!.stopDrag()
                        }
                    }
                } else {
                    left += xTemp
                    right += xTemp

                    if (mOnMoveListener != null) {
                        Log.d(TAG, "stop run")
                        mOnMoveListener!!.startDrag()
                    }
                }
                top += yTemp
                bottom += yTemp
            }

        } else if (!isTouchBitmap(xIndex, yIndex)) {
            if (mOnMoveListener != null) {
                Log.d(TAG, "stop run")
                mOnMoveListener!!.stopDrag()
            }
        }
        invalidate()
        lastIndexX = xIndex
        lastIndexY = yIndex
    }

    /**
     * 计算两指间距
     */
    private fun calculateDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        return sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))
    }

    /**
     * 手指点击点是否在图片上，用于事件分发逻辑判断
     */
    private fun isTouchBitmap(x: Float, y: Float): Boolean {
        if (mBitmap == null) {
            return false
        }
        return mBitmapRect!!.run {
            x in left..right && y in top..bottom
        }
    }

    /**
     * 获取屏幕长宽
     */
    private fun getScreenSize() {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            totalWidth = windowManager.currentWindowMetrics.bounds.width()
            totalHeight = windowManager.currentWindowMetrics.bounds.height()
        } else {
            totalWidth = windowManager.defaultDisplay.width
            totalHeight = windowManager.defaultDisplay.height
        }
    }
}