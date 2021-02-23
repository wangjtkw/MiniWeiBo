package com.example.miniweibo.di

import com.example.miniweibo.ui.MessageFragment
import com.example.miniweibo.ui.VideoFragment
import com.example.miniweibo.ui.home.HomeFragment
import com.example.miniweibo.ui.mine.MineFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeMessageFragment(): MessageFragment

    @ContributesAndroidInjector
    abstract fun contributeVideoFragment(): VideoFragment

    @ContributesAndroidInjector
    abstract fun contributeMineFragment(): MineFragment

}