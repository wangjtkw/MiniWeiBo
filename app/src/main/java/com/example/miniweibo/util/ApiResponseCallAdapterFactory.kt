//package com.example.miniweibo.util
//
//import android.util.Log
//import com.example.miniweibo.api.ApiResponse
//import kotlinx.coroutines.Deferred
//import retrofit2.CallAdapter
//import retrofit2.CallAdapter.Factory
//import retrofit2.Retrofit
//import java.lang.reflect.ParameterizedType
//import java.lang.reflect.Type
//
//class ApiResponseCallAdapterFactory : Factory() {
//    private val TAG = "ApiResponseCallAdapterFactory"
//    override fun get(
//        returnType: Type,
//        annotations: Array<Annotation>,
//        retrofit: Retrofit
//    ): CallAdapter<*, *>? {
//        Log.d(TAG, "运行1")
//        if (getRawType(returnType) != Call::class.java) {
//            Log.d(TAG, "运行2${getRawType(returnType)}")
//            return null
//        }
//        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
//        val rawObservableType = getRawType(observableType)
//        Log.d(TAG, "运行3${rawObservableType}")
//        if (rawObservableType != ApiResponse::class.java) {
//            throw IllegalArgumentException("type must be a resource")
//        }
//        if (observableType !is ParameterizedType) {
//            throw IllegalArgumentException("resource must be parameterized")
//        }
//
//        val bodyType = getParameterUpperBound(0, observableType)
//        Log.d(TAG, "bodyType ${getRawType(bodyType)}")
//        return ApiResponseAdapter<Any>(bodyType)
//    }
//}