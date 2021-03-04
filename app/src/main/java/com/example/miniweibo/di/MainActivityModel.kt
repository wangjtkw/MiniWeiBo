package com.example.miniweibo.di

import androidx.paging.ExperimentalPagingApi
import com.example.miniweibo.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModel {

    @ExperimentalPagingApi
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}