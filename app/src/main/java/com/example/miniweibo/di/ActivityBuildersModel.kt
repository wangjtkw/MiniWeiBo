package com.example.miniweibo.di

import androidx.paging.ExperimentalPagingApi
import com.example.miniweibo.ui.MainActivity
import com.example.miniweibo.ui.infodetail.InfoDetailActivity
import com.example.miniweibo.ui.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModel {

    @ExperimentalPagingApi
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ExperimentalPagingApi
    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @ExperimentalPagingApi
    @ContributesAndroidInjector
    abstract fun contributeInfoDetailActivity(): InfoDetailActivity
}