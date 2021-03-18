package com.example.miniweibo.data.datasource

import androidx.lifecycle.LiveData
import com.example.miniweibo.api.ApiResponse
import com.example.miniweibo.api.WeiBoService
import com.example.miniweibo.data.bean.Resource
import com.example.miniweibo.data.bean.bean.UserInfoBean
import com.example.miniweibo.data.bean.entity.UserInfoEntity
import com.example.miniweibo.data.db.MiniWeiBoDb
import com.example.miniweibo.ext.isConnectedNetwork
import com.example.miniweibo.util.AppHelper
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

        //        Log.d(TAG, result.value?.data.toString())
        return object : ScopeDataSource<UserInfoBean, UserInfoEntity>(scope) {
            override suspend fun loadData(): LiveData<ApiResponse<UserInfoBean>> {
                return api.getUserInfo(access_token, uid)
            }


            override fun loadFromDb() = db.userInfoDao().selectById(uid)

            override fun shouldFetch(data: UserInfoEntity?): Boolean {
                return AppHelper.mContext.isConnectedNetwork()
            }

            override suspend fun saveCallResult(item: UserInfoBean) {
                if (item.id.isEmpty()) {
                    return
                }
                db.userInfoDao().insert(UserInfoEntity.convert2UserInfoEntity(item))
            }


        }.asLiveData()
    }


}