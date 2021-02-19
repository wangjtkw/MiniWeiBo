package com.example.miniweibo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.miniweibo.bean.AccessTokenBean

@Dao
interface AccessTokenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(accessTokenBean: AccessTokenBean)

    @Update
    fun update(accessTokenBean: AccessTokenBean)

    @Query("select * from access_token_bean")
    fun select(): LiveData<AccessTokenBean>

    @Delete
    fun delete(accessTokenBean: AccessTokenBean)

}