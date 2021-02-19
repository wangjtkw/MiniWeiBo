package com.example.miniweibo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.miniweibo.bean.UserInfoBean

@Dao
interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userInfoBean: UserInfoBean)

    @Delete
    fun delete(userInfoBean: UserInfoBean)

    @Query("select * from user_info_bean where id = :id")
    fun selectById(id: String): LiveData<UserInfoBean>
}