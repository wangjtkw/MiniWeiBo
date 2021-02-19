package com.example.miniweibo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.Nullable
import com.example.miniweibo.R
import com.example.miniweibo.sdk.SDKUtil
import com.example.miniweibo.util.ToastUtil

class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"

    private lateinit var loginBt: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        SDKUtil.getSDKUtil().register(this)
        init()
    }

    private fun init() {
        loginBt = findViewById(R.id.login_bt)
        SDKUtil.getSDKUtil()
            .setOnCompleteCallback {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        loginBt.setOnClickListener {
            if (SDKUtil.getSDKUtil().needLogin()) {
                SDKUtil.getSDKUtil().login()
            } else {
                ToastUtil(this).makeToast("已经不需要登录了")
                Log.d(TAG, "start :${System.currentTimeMillis()}")
                Log.d(TAG, SDKUtil.getSDKUtil().getAccessTokenBean().toString())
                Log.d(TAG, "end :${System.currentTimeMillis()}")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        SDKUtil.getSDKUtil().getIWBAPI().authorizeCallback(requestCode, resultCode, data)
    }
}