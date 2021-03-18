package com.example.miniweibo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.miniweibo.data.bean.entity.*

@Database(
    entities = [
        AccessTokenEntity::class,
        UserInfoEntity::class,
        WebInfoEntity::class,
        RemoteKeyEntity::class,
        EmotionEntity::class,
        ImgEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [WebInfoTypeConverter::class])
abstract class MiniWeiBoDb : RoomDatabase() {
    abstract fun accessTokenDao(): AccessTokenDao

    abstract fun userInfoDao(): UserInfoDao

    abstract fun webInfoDao(): WebInfoDao

    abstract fun emotionsDao(): EmotionDao

    abstract fun remoteKeyDao(): RemoteKeyDao

    abstract fun imgDao(): ImgDao

    companion object {

        @Volatile
        private var instance: MiniWeiBoDb? = null

        fun getInstance(context: Context): MiniWeiBoDb {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(application: Context): MiniWeiBoDb {
            return instance ?: Room.databaseBuilder(
                application,
                MiniWeiBoDb::class.java,
                "miniweibo.db"
            )
                .fallbackToDestructiveMigration()//数据库更新时删除数据重新创建
                .build().also { this.instance = it }
        }
    }
}