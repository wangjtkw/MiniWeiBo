package com.example.miniweibo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.miniweibo.R
import com.example.miniweibo.data.bean.bean.WebViewJumpBean
import com.example.miniweibo.util.ToastUtil

class WebViewActivity : AppCompatActivity() {
    private var webView: WebView? = null
    private var mUrl: String = ""
    private var uid: String = ""
    private var webId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        init()
    }

    private fun init() {
        initParam()
        webView = findViewById(R.id.web_web_view)
        load()
    }

    private fun initParam() {
        val param = intent.getParcelableExtra<WebViewJumpBean>(WEB_VIEW_JUMP_PARAM)
        if (param == null || param.url.isNullOrEmpty()) {
            ToastUtil(this).makeToast("请求参数错误")
            finish()
        }
        mUrl = param!!.url ?: ""
        uid = param.uid ?: ""
        webId = param.webId ?: ""
    }

    private fun load() {
        webView!!.loadUrl(mUrl)
        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url ?: "")

                return true
            }
        }
        webView!!.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK && webView!!.canGoBack()) { // 表示按返回键
                // 时的操作
                webView!!.goBack() // 后退
                // webview.goForward();//前进
            } else {
                finish()
            }
            true // 已处理
        }

    }

    companion object {
        const val WEB_VIEW_JUMP_PARAM = "web_view_jump_param"

        fun actionStart(context: Context, webViewJumpBean: WebViewJumpBean) {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(WEB_VIEW_JUMP_PARAM, webViewJumpBean)
            context.startActivity(intent)
        }
    }
}