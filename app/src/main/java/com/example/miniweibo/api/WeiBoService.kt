package com.example.miniweibo.api

import androidx.lifecycle.LiveData
import com.example.miniweibo.data.bean.UserInfoBean
import com.example.miniweibo.data.bean.WebInfoBean
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface WeiBoService {

    /**
     * 根据用户ID获取用户信息
     */
    @GET("2/users/show.json")
    fun getUserInfo(
        @Query("access_token") accessToken: String,
        @Query("uid") uid: String
    ): LiveData<ApiResponse<UserInfoBean>>

    @GET("2/statuses/home_timeline.json")
    suspend fun getHomeTimelineList(
        @Query("access_token") accessToken: String,
        @Query("page") page: Int
    ): ApiResponse<WebInfoBean>


}