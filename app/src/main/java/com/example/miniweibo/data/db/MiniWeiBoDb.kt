package com.example.miniweibo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.miniweibo.data.bean.AccessTokenBean
import com.example.miniweibo.data.bean.UserInfoBean
import com.example.miniweibo.data.bean.WebInfoEntity

@Database(
    entities = [
        AccessTokenBean::class,
        UserInfoBean::class,
        WebInfoEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [WebInfoTypeConverter::class])
abstract class MiniWeiBoDb : RoomDatabase() {
    abstract fun accessTokenDao(): AccessTokenDao

    abstract fun userInfoDao(): UserInfoDao

    abstract fun webInfoDao(): WebInfoDao
}