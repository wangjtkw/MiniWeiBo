package com.example.miniweibo.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.miniweibo.data.bean.entity.AccessTokenEntity

@Dao
interface AccessTokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(accessTokenEntity: AccessTokenEntity)

    @Update
    fun update(accessTokenEntity: AccessTokenEntity)

    @Query("select * from access_token_entity")
    fun select(): LiveData<AccessTokenEntity>

    @Delete
    fun delete(accessTokenEntity: AccessTokenEntity)

}