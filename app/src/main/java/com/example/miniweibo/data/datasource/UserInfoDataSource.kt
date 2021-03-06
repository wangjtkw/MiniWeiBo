package com.example.miniweibo.data.datasource

import androidx.lifecycle.LiveData
import com.example.miniweibo.api.ApiResponse
import com.example.miniweibo.api.WeiBoService
import com.example.miniweibo.data.bean.Resource
import com.example.miniweibo.data.bean.entity.UserInfoEntity
import com.example.miniweibo.data.db.MiniWeiBoDb
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
    ): LiveData<Resource<UserInfoEntity>> {

        val result = object : ScopeDataSource<UserInfoEntity, UserInfoEntity>(scope) {
            override suspend fun loadData(): LiveData<ApiResponse<UserInfoEntity>> {
//                api.getHomeTimelineList(access_token, 1)
                return api.getUserInfo(access_token, uid)
            }


            override fun loadFromDb() = db.userInfoDao().selectById(uid)

            override fun shouldFetch(data: UserInfoEntity?): Boolean {
                return true
            }

            override suspend fun saveCallResult(item: UserInfoEntity) = db.userInfoDao().insert(item)

        }.asLiveData()
//        Log.d(TAG, result.value?.data.toString())
        return result
    }


}