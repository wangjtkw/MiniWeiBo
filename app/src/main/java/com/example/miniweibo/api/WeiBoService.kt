package com.example.miniweibo.api

import androidx.lifecycle.LiveData
import com.example.miniweibo.bean.UserInfoBean
import retrofit2.http.FieldMap
import retrofit2.http.GET

@JvmSuppressWildcards
interface WeiBoService {

    /**
     * 根据用户ID获取用户信息
     */
    @GET("2/users/show.json")
    fun getUserInfo(@FieldMap params: Map<String, Any>): LiveData<ApiResponse<UserInfoBean>>

}