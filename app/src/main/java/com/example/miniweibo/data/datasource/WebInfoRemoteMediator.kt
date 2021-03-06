package com.example.miniweibo.data.datasource

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.miniweibo.api.WeiBoService
import com.example.miniweibo.data.bean.entity.RemoteKeyEntity
import com.example.miniweibo.data.bean.entity.WebInfoEntity
import com.example.miniweibo.data.db.MiniWeiBoDb
import com.example.miniweibo.ext.isConnectedNetwork
import com.example.miniweibo.sdk.SDKUtil
import com.example.miniweibo.util.AppHelper
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class WebInfoRemoteMediator(
    val api: WeiBoService,
    val db: MiniWeiBoDb
) : RemoteMediator<Int, WebInfoEntity>() {
    private val TAG = "WebInfoRemoteMediator"

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, WebInfoEntity>
    ): MediatorResult {
        try {
            val webInfoDao = db.webInfoDao()
            val remoteKeyDao = db.remoteKeyDao()
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = db.withTransaction {
                        remoteKeyDao.getKeyByType(RemoteKeyEntity.TYPE_CONCERN)
                    }

                    if (remoteKey.nextPageKey == null) {
                        Log.d(TAG, "end 1")
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    remoteKey.nextPageKey
                }
            }
            if (!AppHelper.mContext.isConnectedNetwork()) {
                //如果网络未连接，则不进行网络加载
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            val page = loadKey ?: 1
            Log.d(TAG, "PAGE$page")

            val result = api.getHomeTimelineList(
                SDKUtil.getSDKUtil().getAccessTokenBean().accessToken,
                page
            )
            if (result == null) {
                Log.d(TAG, "true 1")
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            if (result.statuses.isNullOrEmpty()) {
                Log.d(TAG, "true2")
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            val items = result.statuses.map {
                WebInfoEntity.convert2WebInfoEntity(it, page + 1)
            }
            if (items.isNullOrEmpty()) {
                Log.d(TAG, "true3")
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    webInfoDao.clearWebInfo()
                    remoteKeyDao.deleteByType(RemoteKeyEntity.TYPE_CONCERN)
                }
                remoteKeyDao.insert(RemoteKeyEntity(RemoteKeyEntity.TYPE_CONCERN, page + 1))
                webInfoDao.insertWebInfo(items)
            }
            return MediatorResult.Success(endOfPaginationReached = false)


//
//            var endOfPaginationReached = true
//            Log.d(TAG, "运行")
//            when (result) {
//                is ApiSuccessResponse -> {
//                    endOfPaginationReached = result.body.statuses.isNullOrEmpty()
//                    val items = result.body.statuses?.map {
//                        WebInfoEntity.convert2WebInfoEntity(it, page + 1)
//                    }
//                    Log.d(TAG, "运行${items!![0].bmiddlePic}")
//                    if (items.isNullOrEmpty()) {
//                        endOfPaginationReached = true
//                    }
//                    db.withTransaction {
//                        if (loadType == LoadType.REFRESH) {
//                            webInfoDao.clearWebInfo()
//                        }
//
//                        webInfoDao.insertWebInfo(items!!)
//                    }
//                    return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
//                }
//                is ApiEmptyResponse -> {
//                    Log.d(TAG, "运行空")
//                    return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
//                }
//                is ApiErrorResponse -> {
//                    Log.d(TAG, "运行错误")
//                    return MediatorResult.Error(Throwable(result.errorMessage))
//                }
//                else -> {
//                    Log.d(TAG, "运行未执行")
//                }
//            }
//
//            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}