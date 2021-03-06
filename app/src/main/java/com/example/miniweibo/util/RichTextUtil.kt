package com.example.miniweibo.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.IconMarginSpan
import android.text.style.ImageSpan
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.miniweibo.MyApplication
import com.example.miniweibo.R
import com.example.miniweibo.data.db.MiniWeiBoDb


/*
Spanned.SPAN_EXCLUSIVE_EXCLUSIVE —— (a,b)

Spanned.SPAN_EXCLUSIVE_INCLUSIVE —— (a,b]

Spanned.SPAN_INCLUSIVE_EXCLUSIVE —— [a,b)

Spanned.SPAN_INCLUSIVE_INCLUSIVE —— [a,b]

 */
class RichTextUtil {
    private var spannableStringBuilder: SpannableStringBuilder? = null
    private var mContent: String = ""
    private val TAG = "RichTextUtil"

    fun init(content: String): RichTextUtil {
        spannableStringBuilder = SpannableStringBuilder(content)
        mContent = content
        return this
    }

    /**
     * 设置 # 闭包
     */
    fun setSharp(): RichTextUtil {
        check()
        val sharpIndexList = findIndex(mContent, '#')
        if (sharpIndexList.isEmpty()) {
            return this
        }
        var p = 0
        while (p < sharpIndexList.size - 1) {
            spannableStringBuilder!!.setSpan(
                ForegroundColorSpan(Color.RED),
                sharpIndexList[p],
                sharpIndexList[p + 1] + 1,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE
            )
            p += 2
        }
        return this
    }

    /**
     * 设置 @ 闭包
     */
    fun setAt(): RichTextUtil {
        check()
        val atIndexList = findIndex(mContent, '@')
        var p = 0
        while (p < atIndexList.size) {
            var end = findAddressEndIndex(mContent, atIndexList[p], ' ')
            if (end == (atIndexList[p] + 1)) {
                p++
                continue
            }
            if (end == -1) {
                end = mContent.length
            }
            spannableStringBuilder!!.setSpan(
                ForegroundColorSpan(Color.RED),
                atIndexList[p],
                end,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            p++
        }
        return this
    }

    fun setAllContent(): RichTextUtil {
        check()
        val textIndex = mContent.lastIndexOf("全文")
        if (textIndex == -1) {
            return this
        }
        spannableStringBuilder!!.setSpan(
            ForegroundColorSpan(Color.parseColor("#1565C0")),
            textIndex,
            textIndex + 2,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        return this
    }

    fun setEmotionTest(context: Context): RichTextUtil {
        val drawable: Drawable? =
            ContextCompat.getDrawable(context, R.drawable.ic_share)
        if (drawable == null) {
            Log.d(TAG, "drawable is null")
            return this
        }
        drawable.setBounds(0, 0, 42, 42)
        val imageSpan = ImageSpan(drawable)
        spannableStringBuilder!!.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }

    fun setEmotion(
        context: Context,
//                           callback: (SpannableStringBuilder) -> Unit
    ): RichTextUtil {
        check()
        val emotionIndexList = findIndex(mContent, '[')
        emotionIndexList.map { index ->
            val end = findAddressEndIndex(mContent, index, ']')
            if (end != -1) {
                val emotion = mContent.substring(index, end + 1)
                val bitmap = getEmotion(emotion, context)
                Log.d(TAG, "表情:$emotion bitmap:$bitmap")
                Log.d(TAG, "表情时间 ${System.currentTimeMillis()}")
                spannableStringBuilder!!.setSpan(
                    ImageSpan(context, bitmap),
                    index,
                    end + 1,
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE
                )
            }
        }
//        callback(spannableStringBuilder!!)
        return this
    }


    private fun getEmotion(
        name: String,
        context: Context
    ): Bitmap {
        val emotionUtil = EmotionUtil(MiniWeiBoDb.getInstance(context).emotionsDao())
        val emotionUrl = emotionUtil.getEmotionUrl(name)
        return PicUtil.loadPic(context, emotionUrl)
    }


    fun build(): SpannableStringBuilder {
        check()
        return spannableStringBuilder!!
    }

//    fun setAddress(): SpannableStringBuilder {
//        check()
//        val addressIndexList = findStr(mContent, "http")
//
//    }


    /**
     * KMP 算法实现
     */
    fun findStr(content: String, target: String): List<Int> {
        var p = 0
        var q = 0
        val slen = content.length
        val plen = target.length
        val next = IntArray(plen)
        getNext(target, next)
        val result = ArrayList<Int>()
        while (p < slen && q < plen) {
            if (content[p] == target[q]) {
                p++
                q++
            } else if (next[q] == -1) {
                p++
                q = 0
            } else {
                q = next[q]
            }
            if (q == plen) {
                result.add(p - q)
//                p++
                q = 0
            }
        }
        return result
    }

    private fun getNext(pattern: String, next: IntArray) {
        var j = 0
        var k = -1
        next[0] = -1
        val len = pattern.length
        while (j < len - 1) {
            if (k == -1 || pattern[j] == pattern[j]) {
                j++
                k++
                next[j] = k
            } else {
                k = next[k]
            }
        }
    }

    fun findIndex(content: String, target: Char): List<Int> {
        var p = 0
        var q = content.length - 1
        val result = ArrayList<Int>()
        while (p <= q) {
            if (content[p] == target) {
                result.add(p)
            }
            if (content[q] == target) {
                result.add(q)
            }
            p++
            q--
        }
        result.sort()
        return result
    }

    fun findAddressEndIndex(content: String, start: Int, endTarget: Char): Int {
        var p = start
        while (p < content.length) {
            if (content[p] == endTarget) {
                return p
            }
            p++
        }
        return -1
    }

    private fun check() {
        require(spannableStringBuilder != null) { "This util must init" }
    }

}