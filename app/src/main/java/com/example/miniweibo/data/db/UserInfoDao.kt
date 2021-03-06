package com.example.miniweibo.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.miniweibo.data.bean.entity.UserInfoEntity

@Dao
interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userInfoEntity: UserInfoEntity)

    @Delete
    fun delete(userInfoEntity: UserInfoEntity)

    @Query("select * from user_info_entity where id = :id")
    fun selectById(id: String): LiveData<UserInfoEntity>
}