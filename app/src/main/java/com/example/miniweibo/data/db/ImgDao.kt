package com.example.miniweibo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.miniweibo.data.bean.entity.EmotionEntity
import com.example.miniweibo.data.bean.entity.ImgEntity

@Dao
interface ImgDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImgUrl(urlList: List<ImgEntity>)

    @Query("select * from img_entity")
    fun selectAll(): LiveData<List<ImgEntity>>

}