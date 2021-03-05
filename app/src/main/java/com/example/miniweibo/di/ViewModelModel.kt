package com.example.miniweibo.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.miniweibo.common.MyViewModelFactory
import com.example.miniweibo.ui.home.concern.HomeConcernViewModel
import com.example.miniweibo.ui.login.LoginViewModel
import com.example.miniweibo.ui.mine.MineViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModel {

    @OptIn(ExperimentalPagingApi::class)
    @Binds
    @IntoMap
    @ViewModelKey(HomeConcernViewModel::class)
    abstract fun bindHomeConcernViewModel(homeConcernViewModel: HomeConcernViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MineViewModel::class)
    abstract fun bindMineViewModel(mineViewModel: MineViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: MyViewModelFactory): ViewModelProvider.Factory

}