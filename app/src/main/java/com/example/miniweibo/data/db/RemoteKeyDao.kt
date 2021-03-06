package com.example.miniweibo.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.miniweibo.data.bean.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: RemoteKeyEntity)

    @Query("SELECT * FROM remote_key_entity WHERE type = :type")
    suspend fun getKeyByType(type: String): RemoteKeyEntity

    @Query("DELETE FROM remote_key_entity WHERE type = :type")
    suspend fun deleteByType(type: String)
}