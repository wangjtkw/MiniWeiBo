package com.example.miniweibo.api

import androidx.lifecycle.LiveData
import com.example.miniweibo.data.bean.bean.EmotionBeanItem
import com.example.miniweibo.data.bean.bean.UserInfoBean
import com.example.miniweibo.data.bean.entity.UserInfoEntity
import com.example.miniweibo.data.bean.bean.WebInfoBean
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

    /**
     * 关注的人的微博消息
     */
    @GET("2/statuses/home_timeline.json")
    suspend fun getHomeTimelineList(
        @Query("access_token") accessToken: String,
        @Query("page") page: Int,
        @Query("count") count:Int
    ): WebInfoBean
//            ApiResponse<>
//            LiveData<ApiResponse<WebInfoBean>>

    /**
     * 根据uid查询用户博客
     */
    @GET("2/statuses/user_timeline.json")
    suspend fun getMineTimelineList(
        @Query("access_token") accessToken: String,
        @Query("uid") uid: String,
        @Query("page") page: Int
    ): WebInfoBean

    /**
     * 获取表情
     */
    //    https://api.weibo.com/2/emotions.json
    @GET("2/emotions.json")
    fun getEmotion(
        @Query("access_token") accessToken: String,
        @Query("type") type: String
    ): LiveData<ApiResponse<List<EmotionBeanItem>>>

}