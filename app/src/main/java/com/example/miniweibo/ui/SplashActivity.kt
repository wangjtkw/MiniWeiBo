package com.example.miniweibo.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.ExperimentalPagingApi
import com.example.miniweibo.R
import com.example.miniweibo.sdk.SDKUtil
import com.example.miniweibo.ui.login.LoginActivity
import android.util.Pair

@ExperimentalPagingApi
class SplashActivity : AppCompatActivity() {
    private val TAG = "SplashActivity"

    private lateinit var splashCL: ConstraintLayout

    private var splashLogoImg: ImageView? = null
    private var splashTv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
//        window.enterTransition = Fade().setDuration(2000)
        startAnim()
    }

    private fun init() {
        splashCL = findViewById(R.id.splash_cl)
        splashLogoImg = findViewById(R.id.splash_logo_img)
        splashTv = findViewById(R.id.splash_tv)
    }

    private fun startAnim() {
        val alphaAnim = AlphaAnimation(0f, 1f)
        alphaAnim.duration = 2000
        alphaAnim.fillAfter = true
//        alphaAnim.repeatCount = 1
//        alphaAnim.repeatMode = Animation.REVERSE
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
            Log.d(TAG, SDKUtil.getSDKUtil().getAccessTokenBean().toString())
            Intent(this, MainActivity::class.java)
        }
        startActivity(
            intent, ActivityOptions.makeSceneTransitionAnimation(
                this,
                Pair<View, String>(splashLogoImg!!, "logo_img"),
                Pair<View, String>(splashTv!!, "splash_tv")
            ).toBundle()
        )

    }

    override fun onStop() {
        super.onStop()
        finish()
    }

}