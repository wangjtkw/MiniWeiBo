package com.example.miniweibo.data.bean.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "img_entity")
data class ImgEntity(@PrimaryKey val url: String) {

    var isFavourite = false

    @Ignore
    var videoUrl: String = ""
}