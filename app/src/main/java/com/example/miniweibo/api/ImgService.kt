package com.example.miniweibo.api

import androidx.lifecycle.LiveData
import com.example.miniweibo.data.bean.bean.UserInfoBean
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface ImgService {

//    http://shibe.online/api/shibes?count=100&urls=true&httpsUrls=false

    @GET("shibes")
    fun getPic(
        @Query("count") count: Int = 100,
        @Query("urls") urls: Boolean = true,
        @Query("httpsUrls") httpsUrls: Boolean = false
    ): LiveData<ApiResponse<List<String>>>
}