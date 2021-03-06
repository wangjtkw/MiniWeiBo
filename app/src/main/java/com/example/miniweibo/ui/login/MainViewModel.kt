package com.example.miniweibo.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniweibo.data.bean.entity.AccessTokenEntity
import com.example.miniweibo.data.bean.entity.EmotionEntity
import com.example.miniweibo.data.bean.Resource
import com.example.miniweibo.data.bean.Status
import com.example.miniweibo.data.datasource.LoginDataSource
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val dataSource: LoginDataSource) : ViewModel() {

    val isLoading = MutableLiveData(true)

    fun getEmotion(accessToken: AccessTokenEntity): LiveData<Resource<List<EmotionEntity>>> {
        val result = dataSource.getEmotion(
            viewModelScope,
            "face",
            accessToken.accessToken
        )
        isLoading.value = (result.value?.status == Status.LOADING)
        return result
    }

}