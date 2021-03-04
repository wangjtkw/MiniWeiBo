package com.example.miniweibo.util

import com.example.miniweibo.api.ApiResponse
import retrofit2.Call
import java.util.concurrent.Executor

class MyCallImpl<T>(private val call: Call<T>, private val callbackExecutor: Executor?) :
    MyCall<T> {
    override fun cancel() {
        call.cancel()
    }

    override fun request(callback: ApiResponse<T>) {

    }
}