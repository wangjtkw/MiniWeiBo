package com.example.miniweibo.ui.mine

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniweibo.data.bean.Resource
import com.example.miniweibo.data.bean.entity.UserInfoEntity
import com.example.miniweibo.data.datasource.UserInfoDataSource
import com.example.miniweibo.sdk.SDKUtil
import javax.inject.Inject

class MineViewModel @Inject constructor(
    userInfoDataSource: UserInfoDataSource,
) : ViewModel() {

    val user: LiveData<Resource<UserInfoEntity>> = userInfoDataSource.getUserInfo(
        viewModelScope,
        SDKUtil.getSDKUtil().getAccessTokenBean().uid,
        SDKUtil.getSDKUtil().getAccessTokenBean().accessToken
    )

}