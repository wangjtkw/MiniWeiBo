package com.example.miniweibo.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.miniweibo.data.bean.entity.WebInfoEntity

@Dao
interface WebInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebInfo(webInfoList: List<WebInfoEntity>)

    @Query("SELECT * FROM web_info_entity where type = :type ORDER BY page ASC")
    fun getWebInfoByType(type: String): PagingSource<Int, WebInfoEntity>

    @Query("DELETE FROM web_info_entity where type = :type")
    suspend fun clearWebInfoByType(type: String)


}