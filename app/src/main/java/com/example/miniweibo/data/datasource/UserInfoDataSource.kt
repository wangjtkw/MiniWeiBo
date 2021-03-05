package com.example.miniweibo.data.datasource

import androidx.lifecycle.LiveData
import com.example.miniweibo.api.ApiResponse
import com.example.miniweibo.api.WeiBoService
import com.example.miniweibo.data.bean.Resource
import com.example.miniweibo.data.bean.UserInfoBean
import com.example.miniweibo.data.db.MiniWeiBoDb
import com.example.miniweibo.data.db.UserInfoDao
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class UserInfoDataSource @Inject constructor(
    private val api: WeiBoService,
    private val db: MiniWeiBoDb
) {
    private val TAG = "UserInfoDataSource"

    fun getUserInfo(
        scope: CoroutineScope,
        uid: String,
        access_token: String
    ): LiveData<Resource<UserInfoBean>> {

        val result = object : ScopeDataSource<UserInfoBean, UserInfoBean>(scope) {
            override suspend fun loadData(): LiveData<ApiResponse<UserInfoBean>> {
//                api.getHomeTimelineList(access_token, 1)
                return api.getUserInfo(access_token, uid)
            }


            override fun loadFromDb() = db.userInfoDao().selectById(uid)

            override fun shouldFetch(data: UserInfoBean?): Boolean {
                return true
            }

            override suspend fun saveCallResult(item: UserInfoBean) = db.userInfoDao().insert(item)

        }.asLiveData()
//        Log.d(TAG, result.value?.data.toString())
        return result
    }


}