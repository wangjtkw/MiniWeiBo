package com.example.miniweibo.ui.home.concern

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.miniweibo.data.bean.entity.WebInfoEntity
import com.example.miniweibo.data.datasource.HomeConcernDataSource
import javax.inject.Inject

@ExperimentalPagingApi
class HomeConcernViewModel @Inject constructor(private val dataSource: HomeConcernDataSource) :
    ViewModel() {

    fun postOfData(): LiveData<PagingData<WebInfoEntity>> =
        dataSource.fetchPokemonList().cachedIn(viewModelScope).asLiveData()
}