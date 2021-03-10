package com.example.miniweibo.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.miniweibo.R
import com.example.miniweibo.data.bean.bean.WebViewJumpBean

class InfoDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_detail)
    }

    companion object {
        const val INFO_DETAIL_PARAM = "info_detail_param"

        fun actionStart(context: Context) {
            val intent = Intent(context, WebViewActivity::class.java)
            context.startActivity(intent)
        }
    }
}