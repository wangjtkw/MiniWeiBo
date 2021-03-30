package com.example.miniweibo.data.bean.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PicUrl(
    @Json(name = "thumbnail_pic")
    val thumbnailPic: String
)