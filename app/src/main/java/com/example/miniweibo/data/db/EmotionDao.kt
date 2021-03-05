package com.example.miniweibo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.miniweibo.data.bean.EmotionBeanItem
import com.example.miniweibo.data.bean.EmotionEntity
import com.example.miniweibo.data.bean.UserInfoBean
import com.example.miniweibo.data.bean.WebInfoEntity

@Dao
interface EmotionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebInfo(webInfoList: List<EmotionEntity>)

    @Query("select * from emotion_entity where value = :name")
    suspend fun selectByName(name: String): EmotionEntity

    @Query("select * from emotion_entity")
    fun selectAll(): LiveData<List<EmotionEntity>>

    @Query("DELETE FROM emotion_entity")
    suspend fun clearEmotions()

}