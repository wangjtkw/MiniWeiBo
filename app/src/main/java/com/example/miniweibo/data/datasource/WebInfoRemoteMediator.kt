package com.example.miniweibo.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.miniweibo.api.ApiEmptyResponse
import com.example.miniweibo.api.ApiErrorResponse
import com.example.miniweibo.api.ApiSuccessResponse
import com.example.miniweibo.api.WeiBoService
import com.example.miniweibo.data.bean.WebInfoEntity
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
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, WebInfoEntity>
    ): MediatorResult {
        try {
            val webInfoDao = db.webInfoDao()

            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    lastItem.page
                }
            }
            if (!AppHelper.mContext.isConnectedNetwork()) {
                //如果网络未连接，则不进行网络加载
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            val page = loadKey ?: 1
            val result =
                api.getHomeTimelineList(
                    SDKUtil.getSDKUtil().getAccessTokenBean().accessToken,
                    page
                )
            var endOfPaginationReached = true
            when (result) {
                is ApiSuccessResponse -> {
                    endOfPaginationReached = result.body.statuses.isNullOrEmpty()
                    val items = result.body.statuses?.map {
                        WebInfoEntity.convert2WebInfoEntity(it, page + 1)
                    }
                    if (items.isNullOrEmpty()) {
                        endOfPaginationReached = true
                    }
                    db.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            webInfoDao.clearWebInfo()
                        }
                        webInfoDao.insertWebInfo(items!!)
                    }
                    return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
                is ApiEmptyResponse -> {
                    return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
                is ApiErrorResponse -> {
                    return MediatorResult.Error(Throwable(result.errorMessage))
                }
            }


        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}