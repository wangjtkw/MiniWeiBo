package com.example.miniweibo.util

import com.example.miniweibo.api.ApiResponse

interface MyCall<T> {

    fun cancel()

    // 发起请求
    fun request(callback: ApiResponse<T>)

}