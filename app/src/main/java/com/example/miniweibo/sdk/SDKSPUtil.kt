package com.example.miniweibo.sdk

import android.content.Context
import com.example.miniweibo.data.bean.AccessTokenBean
import com.example.miniweibo.util.SharedPreferencesUtil

class SDKSPUtil(context: Context) {
    private val FILE_USER_LOGIN = "user_login"
    private val sharedPreferencesUtil = SharedPreferencesUtil.getInstances(FILE_USER_LOGIN, context)

    fun save(accessTokenBean: AccessTokenBean) {
        sharedPreferencesUtil.apply {
            put("uid", accessTokenBean.uid)
            put("user_name", accessTokenBean.userName)
            put("access_token", accessTokenBean.accessToken)
            put("expires_in", accessTokenBean.expiresIn)
            put("refresh_token", accessTokenBean.refreshToken)
            put("is_login", true)
            put("login_time", System.currentTimeMillis())
        }
    }

    fun needLogin(): Boolean {
        sharedPreferencesUtil.apply {
            if (!get("is_login", false)) {
                return true
            }
            val currentTime = System.currentTimeMillis()
            val lastLoginTime = get("login_time", 0L)
            if (currentTime - lastLoginTime > 86400000L) {
                return true
            }
        }
        return false
    }

    fun getAccessTokenBean(): AccessTokenBean {
        sharedPreferencesUtil.apply {
            val uid = get("uid", "")
            val userName = get("user_name", "")
            val accessToken = get("access_token", "")
            val expiresIn = get("expires_in", 0L)
            val refreshToken = get("refresh_token", "")
            return AccessTokenBean(uid, userName, accessToken, expiresIn, refreshToken)
        }
    }

}