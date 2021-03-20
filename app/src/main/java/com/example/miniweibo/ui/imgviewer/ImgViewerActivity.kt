package com.example.miniweibo.ui.imgviewer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.miniweibo.R
import com.example.miniweibo.data.bean.bean.ImgWrapBean
import com.example.miniweibo.ext.isConnectedNetwork
import com.example.miniweibo.myview.ImgViewer
import com.example.miniweibo.util.ToastUtil

class ImgViewerActivity : AppCompatActivity() {
    private var imgViewer: ImgViewer? = null

    private var mUrl: ImgWrapBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img_viewer)
        getParam()
        init()
    }

    private fun getParam() {
        mUrl = intent.getParcelableExtra(IMG_VIEWER_PARAM_URL)
    }

    private fun init() {
        imgViewer = findViewById(R.id.img_viewer_viewer)
        if (!isConnectedNetwork()) {
            ToastUtil.makeToast("当前网络未连接！")
        }
        imgViewer!!.setImg(R.drawable.bg_img)
        imgViewer!!.setImg(mUrl?.url)
        imgViewer!!.setImg(mUrl?.originUrl)
    }

    companion object {
        private const val IMG_VIEWER_PARAM_URL = "img_viewer_param_url"

        fun actionStart(context: Context, url: ImgWrapBean) {
            val intent = Intent(context, ImgViewerActivity::class.java)
            intent.putExtra(IMG_VIEWER_PARAM_URL, url)
            context.startActivity(intent)
        }
    }
}