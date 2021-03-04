package com.example.miniweibo.data.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.miniweibo.api.ApiEmptyResponse
import com.example.miniweibo.api.ApiErrorResponse
import com.example.miniweibo.api.ApiResponse
import com.example.miniweibo.api.ApiSuccessResponse
import com.example.miniweibo.data.bean.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * [RequestType]:网络请求的返回结果
 * [ResultType]:请求数据库后的返回结果
 */
abstract class ScopeDataSource<RequestType, ResultType>(private val scope: CoroutineScope) {
    private val TAG = "ScopeDataSource"

    private var result = MediatorLiveData<Resource<ResultType>>()

    init {

        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
//        Log.d(TAG, "$dbSource")
        result.addSource(dbSource) { data ->
//            Log.d(TAG, "切换 运行 ${data}")
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
//                Log.d(TAG, "网络运行")
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData))
                }
            }
        }
    }


    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        scope.launch(Dispatchers.IO) {
            val apiResponse = loadData()
            launch(Dispatchers.Main) {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.loading(newData))
                }
                result.addSource(apiResponse) { response ->
                    result.removeSource(apiResponse)
                    result.removeSource(dbSource)
                    when (response) {
                        is ApiSuccessResponse -> {
                            scope.launch(Dispatchers.IO) {
                                //先将数据保存到数据库，再从数据库拉取，保证唯一稳定数据源
                                saveCallResult(processResponse(response))
                                launch(Dispatchers.Main) {
                                    result.addSource(loadFromDb()) { newData ->
                                        setValue(Resource.success(newData))
                                    }
                                }
                            }
                        }
                        is ApiEmptyResponse -> {
                            scope.launch(Dispatchers.Main) {
                                result.addSource(loadFromDb()) { newData ->
                                    setValue(Resource.success(newData))
                                }
                            }
                        }
                        is ApiErrorResponse -> {
                            onFetchFailed()
                            result.addSource(dbSource) { newData ->
                                setValue(Resource.error(response.errorMessage, newData))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    /**
     * 从网络加载数据
     */
    protected abstract suspend fun loadData(): LiveData<ApiResponse<RequestType>>

    /**
     * 从数据库加载数据
     */
    protected abstract fun loadFromDb(): LiveData<ResultType>

    /**
     * 是否从网络拉取数据
     */
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    /**
     * 将网络返回数据保存到数据库中
     */
    protected abstract fun saveCallResult(item: RequestType)

    /**
     * 对数据进行处理，方便存储
     */
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    /**
     * 进行异常处理
     */
    protected open fun onFetchFailed() {}
}