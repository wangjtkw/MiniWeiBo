package com.example.miniweibo.myview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class ImgViewerPager : ViewPager, OnChildMovingListener {
    private var mChildIsBeingDragged = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if (mChildIsBeingDragged) {
            false
        } else {
            super.onInterceptTouchEvent(ev)
        }
    }

    override fun startDrag() {
        mChildIsBeingDragged = true
    }

    override fun stopDrag() {
        mChildIsBeingDragged = false
    }


}

interface OnChildMovingListener {
    fun startDrag()
    fun stopDrag()
}