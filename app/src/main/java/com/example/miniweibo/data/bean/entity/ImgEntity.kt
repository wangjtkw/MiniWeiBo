package com.example.miniweibo.data.bean.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "img_entity")
data class ImgEntity(val url: String) {
    @PrimaryKey(autoGenerate = true)
    var emotionId: Int = 0
}