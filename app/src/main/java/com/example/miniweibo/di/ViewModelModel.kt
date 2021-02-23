package com.example.miniweibo.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miniweibo.common.MyViewModelFactory
import com.example.miniweibo.ui.mine.MineViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModel {

    @Binds
    @IntoMap
    @ViewModelKey(MineViewModel::class)
    abstract fun bindMineViewModel(mineViewModel: MineViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: MyViewModelFactory): ViewModelProvider.Factory

}