package com.example.miniweibo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.miniweibo.bean.AccessTokenBean
import com.example.miniweibo.bean.UserInfoBean

@Database(
    entities = [AccessTokenBean::class, UserInfoBean::class],
    version = 1,
    exportSchema = false
)
abstract class MiniWeiBoDb : RoomDatabase() {
    abstract fun accessTokenDao(): AccessTokenDao

    abstract fun userInfoDao(): UserInfoDao
}