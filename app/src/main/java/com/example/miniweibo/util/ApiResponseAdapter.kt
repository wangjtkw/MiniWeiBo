package com.example.miniweibo.util

import android.util.Log
import com.example.miniweibo.R
import com.example.miniweibo.api.ApiResponse
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.*
import java.lang.reflect.Type

//class ApiResponseAdapter<T>(private val responseType: Type) :
//    CallAdapter<T, Call<ApiResponse<T>>> {
//    private val TAG = "ApiResponseAdapter"
//    override fun responseType() = responseType
//    override fun adapt(call: Call<T>): Call<ApiResponse<T>> {
//
//    }
//
//    //    ApiResponse<T>
//
//}