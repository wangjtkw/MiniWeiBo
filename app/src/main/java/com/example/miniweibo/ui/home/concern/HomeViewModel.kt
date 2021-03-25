package com.example.miniweibo.ui.home.concern

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.miniweibo.data.bean.entity.WebInfoEntity
import com.example.miniweibo.data.datasource.WebInfoDataSource
import javax.inject.Inject

@ExperimentalPagingApi
class HomeViewModel @Inject constructor(private val dataSource: WebInfoDataSource) :
    ViewModel() {

    fun postOfConcernData(): LiveData<PagingData<WebInfoEntity>> =
        dataSource.fetchConcernPokemonList().cachedIn(viewModelScope).asLiveData()

    fun postOfMineData(type: String): LiveData<PagingData<WebInfoEntity>> =
        dataSource.fetchTypePokemonList(type).cachedIn(viewModelScope).asLiveData()
}