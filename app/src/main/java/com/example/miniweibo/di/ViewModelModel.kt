package com.example.miniweibo.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.miniweibo.common.MyViewModelFactory
import com.example.miniweibo.ui.home.concern.HomeViewModel
import com.example.miniweibo.ui.infodetail.InfoDetailViewModel
import com.example.miniweibo.ui.login.LoginViewModel
import com.example.miniweibo.ui.mine.MineViewModel
import com.example.miniweibo.ui.video.VideoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModel {

    @OptIn(ExperimentalPagingApi::class)
    @Binds
    @IntoMap
    @ViewModelKey(VideoViewModel::class)
    abstract fun bindVideoViewModel(videoViewModel: VideoViewModel): ViewModel


    @OptIn(ExperimentalPagingApi::class)
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeConcernViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MineViewModel::class)
    abstract fun bindMineViewModel(mineViewModel: MineViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @OptIn(ExperimentalPagingApi::class)
    @Binds
    @IntoMap
    @ViewModelKey(InfoDetailViewModel::class)
    abstract fun bindInfoDetailViewModel(infoDetailViewModel: InfoDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: MyViewModelFactory): ViewModelProvider.Factory

}