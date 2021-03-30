package com.example.miniweibo.data.bean.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RetweetedStatus(
    @Json(name = "bmiddle_pic")
    val bmiddlePic: String?,
    @Json(name = "idstr")
    val idstr: String?,
    @Json(name = "original_pic")
    val originalPic: String?,
    @Json(name = "pic_num")
    val picNum: Int?,
    @Json(name = "picStatus")
    val picStatus: String?,
    @Json(name = "pic_urls")
    val picUrls: List<PicUrl>?,
    @Json(name = "text")
    val text: String?,
    @Json(name = "textLength")
    val textLength: Int?,
    @Json(name = "thumbnail_pic")
    val thumbnailPic: String?,
    @Json(name = "user")
    val user: User?
)