package com.example.miniweibo.datasource

import androidx.lifecycle.LiveData
import com.example.miniweibo.api.WeiBoService
import com.example.miniweibo.bean.Resource
import com.example.miniweibo.bean.UserInfoBean
import com.example.miniweibo.db.UserInfoDao
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class UserInfoDataSource @Inject constructor(
    private val api: WeiBoService,
    private val dao: UserInfoDao
) {

    fun getUserInfo(
        scope: CoroutineScope,
        uid: String,
        access_token: String
    ): LiveData<Resource<UserInfoBean>> {
        return object : ScopeDataSource<UserInfoBean, UserInfoBean>(scope) {
            override fun loadData() =
                api.getUserInfo(mapOf("access_token" to access_token, "uid" to uid))

            override fun loadFromDb() = dao.selectById(uid)

            override fun shouldFetch(data: UserInfoBean?): Boolean {
                return true
            }

            override fun saveCallResult(item: UserInfoBean)  = dao.insert(item)

        }.asLiveData()
    }


}