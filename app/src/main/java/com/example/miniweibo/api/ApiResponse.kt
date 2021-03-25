package com.example.miniweibo.api

import retrofit2.Response

//利用密封类来对网络请求结果进行分类，供后续使用
@JvmSuppressWildcards
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            if (response.isSuccessful) {
                val body = response.body()
                return if (body == null || response.code() == 204 || response.code() == 403) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body)
                }
            } else {
                val msg = response.errorBody()?.toString()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                return ApiErrorResponse(errorMsg)
            }
        }
    }

}

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()