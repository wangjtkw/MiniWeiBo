package com.example.miniweibo.sdk

import android.content.Context
import android.util.Log
import com.example.miniweibo.data.bean.entity.AccessTokenEntity
import com.example.miniweibo.constants.PlatformParameters
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.openapi.IWBAPI
import com.sina.weibo.sdk.openapi.WBAPIFactory
import java.lang.Exception

class SDKUtil private constructor(context: Context) {
    private val TAG = "SDKUtil"

    private var sdkHelper: SDKHelper? = null

    private var mWBAPI: IWBAPI? = null

    init {
        sdkHelper = SDKHelper(context)
    }

    fun register(context: Context) {
        val authInfo = AuthInfo(
            context,
            PlatformParameters.APP_KEY,
            PlatformParameters.REDIRECT_URI,
            PlatformParameters.SCOPE
        )
        mWBAPI = WBAPIFactory.createWBAPI(context)
        mWBAPI?.registerApp(context, authInfo)
    }

    fun getIWBAPI(): IWBAPI {
        return mWBAPI!!
    }

    fun getAccessTokenBean(): AccessTokenEntity {
        return if (sdkHelper != null) {
            sdkHelper!!.sdkSpUtil.getAccessTokenBean()
        } else {
            throw Exception("SDKUtil is not init")
        }
    }

    fun needLogin(): Boolean {
        if (sdkHelper != null) {
            return sdkHelper!!.sdkSpUtil.needLogin()
        } else {
            throw Exception("SDKUtil is not init")
        }
    }

    fun login() {
        if (mWBAPI == null) {
            Log.d(TAG, "mWBAPI == null")
        }
        if (sdkHelper == null) {
            Log.d(TAG, "wbAuthListener == null")
            throw Exception("SDKUtil is not init")
        }
        Log.d(TAG, "running")

        mWBAPI?.authorizeClient(sdkHelper!!.wbAuthListener)
    }

    fun setOnCompleteCallback(callback: (AccessTokenEntity) -> Unit) {
        sdkHelper!!.wbAuthListener.onCompleteCallback = callback
    }

    companion object {
        private var sdkUtil: SDKUtil? = null

        fun init(context: Context) {
            if (sdkUtil == null) {
                sdkUtil = SDKUtil(context)
            }
        }

        fun getSDKUtil(): SDKUtil {
            return if (sdkUtil != null) {
                sdkUtil!!
            } else {
                throw Exception("SDKUtil is not init")
            }
        }
    }
}