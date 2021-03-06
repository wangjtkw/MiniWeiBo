package com.example.miniweibo.data.bean.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emotion_entity")
data class EmotionEntity(
    //链接
    @ColumnInfo(name = "icon")
    val icon: String,
    //表情名
    @ColumnInfo(name = "value")
    val value: String,
    @ColumnInfo(name = "load_time")
    val loadTime: Long,
    @ColumnInfo(name = "local_url")
    var localUrl: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var emotionId: Int = 0
}