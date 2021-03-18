package com.example.miniweibo.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.miniweibo.api.WeiBoService
import com.example.miniweibo.data.bean.entity.RemoteKeyEntity
import com.example.miniweibo.data.bean.entity.WebInfoEntity
import com.example.miniweibo.data.db.MiniWeiBoDb
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeConcernDataSource @Inject constructor(
    val api: WeiBoService,
    val db: MiniWeiBoDb
) {
    /**
     * 加载关注的人的微博
     */
    @ExperimentalPagingApi
    fun fetchConcernPokemonList(): Flow<PagingData<WebInfoEntity>> {
        val pagingConfig = PagingConfig(
            // 每页显示的数据的大小
            pageSize = 15,
            // 开启占位符
            enablePlaceholders = true,
            // 预刷新的距离，距离最后一个 item 多远时加载数据
            // 默认为 pageSize
            prefetchDistance = 4,
            /**
             * 初始化加载数量，默认为 pageSize * 3
             * internal const val DEFAULT_INITIAL_PAGE_MULTIPLIER = 3
             * val initialLoadSize: Int = pageSize * DEFAULT_INITIAL_PAGE_MULTIPLIER
             */
            initialLoadSize = 45
        )
        return Pager(
            config = pagingConfig,
            remoteMediator = WebInfoRemoteMediator(api, db, RemoteKeyEntity.TYPE_CONCERN)
        ) {
            db.webInfoDao().getWebInfoByType(RemoteKeyEntity.TYPE_CONCERN)
        }.flow
    }

    /**
     * 根据用户名加载微博
     */
    @ExperimentalPagingApi
    fun fetchTypePokemonList(type: String): Flow<PagingData<WebInfoEntity>> {
        val pagingConfig = PagingConfig(
            pageSize = 15,
            enablePlaceholders = true,
            prefetchDistance = 4,
            initialLoadSize = 45
        )
        return Pager(
            config = pagingConfig,
            remoteMediator = WebInfoRemoteMediator(api, db, type)
        ) {
            db.webInfoDao().getWebInfoByType(type)
        }.flow
    }


}