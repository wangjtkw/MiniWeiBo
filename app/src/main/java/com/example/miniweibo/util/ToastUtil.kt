package com.example.miniweibo.util

import android.content.Context
import android.widget.Toast
import com.example.miniweibo.MyApplication
import javax.inject.Inject

class ToastUtil @Inject constructor(val context: Context) {
    private var lastShowTime = 0L
    private var lastShowMsg: String? = null
    private var curShowMsg: String? = null
    private var TOAST_DURATION = 2000
    private var toast: Toast? = null

    fun makeToast(message: CharSequence) {
        curShowMsg = message.toString()
        val currentShowTime = System.currentTimeMillis()
        val duration = currentShowTime - lastShowTime
        if (curShowMsg.equals(lastShowMsg) && duration <= TOAST_DURATION) {
            return
        }
        lastShowTime = currentShowTime
        lastShowMsg = curShowMsg
        if (toast == null) {
            toast = Toast.makeText(context, curShowMsg, Toast.LENGTH_SHORT)
        } else {
            toast?.setText(curShowMsg)
        }
        toast?.show()
    }
}