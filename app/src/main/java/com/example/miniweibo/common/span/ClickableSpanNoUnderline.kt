package com.example.miniweibo.common.span

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import androidx.annotation.NonNull

abstract class ClickableSpanNoUnderline(
    private val color: Int,
    private val mOnClickListener: OnClickListener?
) :
    ClickableSpan() {
    constructor(onClickListener: OnClickListener) : this(NO_COLOR, onClickListener) {}

    /**
     * Makes the text underlined and in the link color.
     *
     * @param ds
     */
    override fun updateDrawState(@NonNull ds: TextPaint) {
        super.updateDrawState(ds)
        // 设置文字颜色
        if (color == NO_COLOR) {
            ds.color = ds.linkColor
        } else {
            ds.color = color
        }
        ds.clearShadowLayer()
        // 去除下划线
        ds.isUnderlineText = false
        ds.bgColor = Color.TRANSPARENT
    }

    /**
     * Performs the click action associated with this span.
     *
     * @param widget widget
     */
    override fun onClick(widget: View) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(widget, this)
        } else {
            Log.w(TAG, "listener was null")
        }
    }


    companion object {
        private const val TAG = "ClickableSpan"
        private const val NO_COLOR = -206
    }
}

/**
 * 回调接口，回调自身的onClick事件
 * 告诉外部 是否被点击
 */
interface OnClickListener {
    /**
     * ClickableSpan被点击
     *
     * @param widget widget
     * @param span   span
     */
    fun <T> onClick(widget: View?, span: T)
}
