package com.example.miniweibo.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.miniweibo.api.ApiResponse
import com.example.miniweibo.api.WeiBoService
import com.example.miniweibo.data.bean.*
import com.example.miniweibo.data.db.MiniWeiBoDb
import com.example.miniweibo.util.FrescoUtil
import com.example.miniweibo.util.TimeUtil
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    private val api: WeiBoService,
    private val db: MiniWeiBoDb
) {
    private val TAG = "LoginDataSource"

    fun getEmotion(
        scope: CoroutineScope,
        type: String,
        access_token: String
    ): LiveData<Resource<List<EmotionEntity>>> {
        return object : ScopeDataSource<List<EmotionBeanItem>, List<EmotionEntity>>(scope) {
            override suspend fun loadData(): LiveData<ApiResponse<List<EmotionBeanItem>>> {
                return api.getEmotion(access_token, type)
            }

            override suspend fun saveCallResult(item: List<EmotionBeanItem>) {
                val dbList = ArrayList<EmotionEntity>()
                Log.d(TAG, "run")
                item.map {
                    FrescoUtil.downloadPic(it.url)
                    val entity = EmotionEntity(it.icon, it.value, TimeUtil.getCurrentTimestamp())
                    dbList.add(entity)
                }
                db.emotionsDao().insertWebInfo(dbList)
            }

            override fun loadFromDb(): LiveData<List<EmotionEntity>> = db.emotionsDao().selectAll()

            override fun shouldFetch(data: List<EmotionEntity>?): Boolean {
                if (data.isNullOrEmpty()) {
                    return true
                }
                val currentTimeTimestamp = TimeUtil.getCurrentTimestamp()
                val differ = currentTimeTimestamp - data[0].loadTime
                if (differ > data[0].loadTime) {
                    return true
                }
                return false
            }

            override fun onFetchFailed() {
                super.onFetchFailed()
            }
        }.asLiveData()
    }


}