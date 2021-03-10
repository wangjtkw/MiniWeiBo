package com.example.miniweibo.data.datasource

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.miniweibo.api.WeiBoService
import com.example.miniweibo.data.bean.bean.WebInfoBean
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
    val db: MiniWeiBoDb,
    val type: String
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

                    var remoteKey: RemoteKeyEntity? = null
                    when (type) {
                        RemoteKeyEntity.TYPE_CONCERN -> {
                            remoteKey = db.withTransaction {
                                remoteKeyDao.getKeyByType(RemoteKeyEntity.TYPE_CONCERN)
                            }
                        }
                        else -> {
                            remoteKey = db.withTransaction {
                                remoteKeyDao.getKeyByType(type)
                            }
                        }
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
            var result: WebInfoBean? = null
            val accessToken = SDKUtil.getSDKUtil().getAccessTokenBean().accessToken
            result = when (type) {
                RemoteKeyEntity.TYPE_CONCERN -> {
                    api.getHomeTimelineList(
                        accessToken,
                        page
                    )
                }
                else -> {
                    api.getMineTimelineList(
                        accessToken, type, page
                    )
                }
            }

            if (result == null) {
                Log.d(TAG, "true 1")
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            if (result.statuses.isNullOrEmpty()) {
                Log.d(TAG, "true2")
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            val items = result.statuses!!.map {
                WebInfoEntity.convert2WebInfoEntity(it, page + 1, type)
            }
            if (items.isNullOrEmpty()) {
                Log.d(TAG, "true3")
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    webInfoDao.clearWebInfoByType(type)
                    remoteKeyDao.deleteByType(type)
                }
                remoteKeyDao.insert(RemoteKeyEntity(type, page + 1))
                webInfoDao.insertWebInfo(items)
            }
            return MediatorResult.Success(endOfPaginationReached = false)

        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}