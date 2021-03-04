package com.example.miniweibo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.ExperimentalPagingApi
import com.example.miniweibo.R
import com.example.miniweibo.sdk.SDKUtil

@ExperimentalPagingApi
class SplashActivity : AppCompatActivity() {
    private val TAG = "SplashActivity"

    private lateinit var splashCL: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
        startAnim()
    }

    private fun init() {
        splashCL = findViewById(R.id.splash_cl)
    }

    private fun startAnim() {
        val alphaAnim = AlphaAnimation(0f, 1f)
        alphaAnim.duration = 2000
        alphaAnim.fillAfter = true
        alphaAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                jumpToNext()
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

        })
        splashCL.animation = alphaAnim
    }

    private fun jumpToNext() {
        val intent: Intent = if (SDKUtil.getSDKUtil().needLogin()) {
            Intent(this, LoginActivity::class.java)
        } else {
            Log.d(TAG,SDKUtil.getSDKUtil().getAccessTokenBean().toString())
            Intent(this, MainActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}