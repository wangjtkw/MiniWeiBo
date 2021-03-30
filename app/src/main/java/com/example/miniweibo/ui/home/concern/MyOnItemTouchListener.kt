package com.example.miniweibo.ui.home.concern

import android.view.MotionEvent
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

abstract class MyOnItemTouchListener<VH : RecyclerView.ViewHolder>
    (recyclerView: RecyclerView) : RecyclerView.OnItemTouchListener {

    private var mGestureDetectorCompat: GestureDetectorCompat? = null
    private var myGestureListener: MyGestureListener<VH>? = null

    init {
        myGestureListener = MyGestureListener(recyclerView) {
            onItemClick(it)
        }
        mGestureDetectorCompat = GestureDetectorCompat(recyclerView.context, myGestureListener)
    }


    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        mGestureDetectorCompat!!.onTouchEvent(e)
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

    abstract fun onItemClick(viewHolder: VH)
}