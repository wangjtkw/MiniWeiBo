package com.example.miniweibo.ui.infodetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.example.miniweibo.data.datasource.UserInfoDataSource
import com.example.miniweibo.data.datasource.WebInfoDataSource
import com.example.miniweibo.sdk.SDKUtil
import javax.inject.Inject

@ExperimentalPagingApi
class InfoDetailViewModel @Inject constructor(
    private val userInfoDataSource: UserInfoDataSource,
    private val webInfoDataSource: WebInfoDataSource
) : ViewModel() {

    fun getUserInfo(uid: String) = userInfoDataSource.getUserInfo(
        viewModelScope,
        uid,
        SDKUtil.getSDKUtil().getAccessTokenBean().accessToken
    )

    fun getWebInfo(uid: String) =
        webInfoDataSource.fetchTypePokemonList(uid).cachedIn(viewModelScope).asLiveData()

}