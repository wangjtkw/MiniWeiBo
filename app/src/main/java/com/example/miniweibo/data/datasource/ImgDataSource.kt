package com.example.miniweibo.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.miniweibo.api.ApiErrorResponse
import com.example.miniweibo.api.ApiResponse
import com.example.miniweibo.api.ImgService
import com.example.miniweibo.api.WeiBoService
import com.example.miniweibo.data.bean.Resource
import com.example.miniweibo.data.bean.bean.EmotionBeanItem
import com.example.miniweibo.data.bean.entity.EmotionEntity
import com.example.miniweibo.data.bean.entity.ImgEntity
import com.example.miniweibo.data.db.MiniWeiBoDb
import com.example.miniweibo.ext.isConnectedNetwork
import com.example.miniweibo.util.AppHelper
import com.example.miniweibo.util.PicUtil
import com.example.miniweibo.util.TimeUtil
import kotlinx.coroutines.CoroutineScope
import java.lang.reflect.Array
import javax.inject.Inject

class ImgDataSource @Inject constructor(
    private val api: ImgService,
    private val db: MiniWeiBoDb
) {

    fun getImg(scope: CoroutineScope): LiveData<Resource<List<ImgEntity>>> {
        return object : ScopeDataSource<List<String>, List<ImgEntity>>(scope) {
            override suspend fun loadData(): LiveData<ApiResponse<List<String>>> {
                return api.getPic()
            }

            override fun loadFromDb(): LiveData<List<ImgEntity>> {
                return db.imgDao().selectAll()
            }

            override fun shouldFetch(data: List<ImgEntity>?): Boolean {
                return AppHelper.mContext.isConnectedNetwork()
            }

            override suspend fun saveCallResult(item: List<String>) {
                val imgEntityList = ArrayList<ImgEntity>()
                item.map {
                    imgEntityList.add(ImgEntity(it))
                }
                db.imgDao().insertImgUrl(imgEntityList)
            }

        }.asLiveData()
    }
}