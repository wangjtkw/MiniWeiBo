package com.example.miniweibo.ui.login

import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.miniweibo.R
import com.example.miniweibo.data.bean.Status
import com.example.miniweibo.databinding.ActivityLoginBinding
import com.example.miniweibo.ext.isConnectedNetwork
import com.example.miniweibo.sdk.SDKUtil
import com.example.miniweibo.ui.MainActivity
import com.example.miniweibo.util.AppHelper
import com.example.miniweibo.util.ToastUtil
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

@ExperimentalPagingApi
class LoginActivity : AppCompatActivity(), HasAndroidInjector {
    private val TAG = "LoginActivity"

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var binding: ActivityLoginBinding? = null

    private val loginViewModel: LoginViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
//        window.enterTransition = Explode ().setDuration(200);
//        getWindow().setExitTransition(new Explode ().setDuration(2000));

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        SDKUtil.getSDKUtil().register(this)
        init()
    }

    private fun init() {
        ViewCompat.animate(binding!!.loginBt)
            .setDuration(500)
            .translationY(-150f)
            .start()

        noNetworkToast()

        SDKUtil.getSDKUtil().setOnCompleteCallback { accessTokenBean ->
            loginViewModel.getEmotion(accessTokenBean).observe(this) { resources ->
                binding!!.resource = resources
                if (resources.status != Status.LOADING) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }


        binding!!.loginBt.setOnClickListener {
            if (!AppHelper.mContext.isConnectedNetwork()) {
                noNetworkToast()
            } else {
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
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onPause() {
        super.onPause()
        binding!!.loginGroup.visibility = View.GONE
    }

    private fun noNetworkToast() {
        if (!AppHelper.mContext.isConnectedNetwork()) {
            //如果网络未连接，则不进行网络加载
            ToastUtil(this).makeToast("当前网络未连接")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        SDKUtil.getSDKUtil().getIWBAPI().authorizeCallback(requestCode, resultCode, data)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}