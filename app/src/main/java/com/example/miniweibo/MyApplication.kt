package com.example.miniweibo

import android.app.Application
import androidx.multidex.MultiDex
import com.example.miniweibo.di.AppInjector
import com.example.miniweibo.sdk.SDKUtil
import com.example.miniweibo.util.AppHelper
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class MyApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun onCreate() {
        super.onCreate()
        AppHelper.init(this)
        MultiDex.install(this)
        AppInjector.init(this)
        SDKUtil.init(this)
        Fresco.initialize(this)
    }

    override fun androidInjector() = dispatchingAndroidInjector

}