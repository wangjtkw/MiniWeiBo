package com.example.miniweibo.di

import com.example.miniweibo.MyApplication

object AppInjector {

    fun init(application: MyApplication) {
        DaggerAppComponent.builder().application(application).build().inject(application)
    }
}