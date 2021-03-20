package com.example.miniweibo.ui.home.concern

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class MyGestureListener<VH : RecyclerView.ViewHolder>(
    private val recyclerView: RecyclerView,
    private val callback: (VH) -> Unit
) :
    GestureDetector.SimpleOnGestureListener() {
    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        val child: View? = recyclerView.findChildViewUnder(e!!.x, e.y)
        if (child != null) {
            val viewHolder = recyclerView.getChildViewHolder(child)
            callback(viewHolder as VH)
        }
        return true
    }
}