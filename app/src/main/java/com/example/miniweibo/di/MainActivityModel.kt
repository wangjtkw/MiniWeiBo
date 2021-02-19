package com.example.miniweibo.di

import com.example.miniweibo.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModel {

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity
}