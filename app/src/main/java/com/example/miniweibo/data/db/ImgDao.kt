package com.example.miniweibo.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.miniweibo.data.bean.entity.ImgEntity

@Dao
interface ImgDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImgUrl(urlList: List<ImgEntity>)

    @Query("select * from img_entity")
    fun selectAll(): LiveData<List<ImgEntity>>

    @Update
    fun update(imgEntity: ImgEntity)

}