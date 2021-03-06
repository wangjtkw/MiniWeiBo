package com.example.miniweibo.sdk

import android.content.Context
import android.util.Log
import com.example.miniweibo.data.bean.entity.AccessTokenEntity
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WbAuthListener
import com.sina.weibo.sdk.common.UiError


class SDKHelper constructor(context: Context) {
    var wbAuthListener: WbAuthListenerImpl = WbAuthListenerImpl()
    val sdkSpUtil: SDKSPUtil = SDKSPUtil(context)

    inner class WbAuthListenerImpl : WbAuthListener {

        var onCompleteCallback: (AccessTokenEntity) -> Unit = {
            Log.d(TAG, "未切换")
        }

        private val TAG = "WbAuthListenerImpl"

        override fun onComplete(p0: Oauth2AccessToken?) {
            var accessTokenEntity: AccessTokenEntity?
            p0?.apply {
                accessTokenEntity =
                    AccessTokenEntity(
                        uid = uid,
                        userName = screenName,
                        accessToken = accessToken,
                        expiresIn = expiresTime,
                        refreshToken = refreshToken
                    )
                sdkSpUtil.save(accessTokenEntity!!)
                onCompleteCallback(accessTokenEntity!!)
                Log.d(TAG, "onCompleteCallback ----> $onCompleteCallback")
                Log.d(TAG, "onComplete ----> $accessTokenEntity")
            }
        }

        override fun onError(p0: UiError?) {
            p0?.let {
                val errorCode = it.errorCode
                val errorMessage = it.errorMessage
                val errorDetail = it.errorMessage
                Log.d(
                    TAG,
                    "onError ----> errorCode:$errorCode errorMessage:$errorMessage errorDetail:$errorDetail"
                )
            }
        }

        override fun onCancel() {
            Log.d(TAG, "onCancel ---->")
        }
    }
}