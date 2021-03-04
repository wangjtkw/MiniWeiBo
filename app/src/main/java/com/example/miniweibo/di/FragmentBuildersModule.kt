package com.example.miniweibo.di

import androidx.paging.ExperimentalPagingApi
import com.example.miniweibo.ui.MessageFragment
import com.example.miniweibo.ui.VideoFragment
import com.example.miniweibo.ui.home.HomeFragment
import com.example.miniweibo.ui.home.concern.HomeConcernFragment
import com.example.miniweibo.ui.mine.MineFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ExperimentalPagingApi
    @ContributesAndroidInjector
    abstract fun contributeHomeConcernFragment(): HomeConcernFragment

    @ExperimentalPagingApi
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeMessageFragment(): MessageFragment

    @ContributesAndroidInjector
    abstract fun contributeVideoFragment(): VideoFragment

    @ContributesAndroidInjector
    abstract fun contributeMineFragment(): MineFragment

}