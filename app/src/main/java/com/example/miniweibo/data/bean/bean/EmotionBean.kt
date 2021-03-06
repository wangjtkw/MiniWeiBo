package com.example.miniweibo.data.bean.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmotionItem(
    @Json(name = "category")
    val category: String,
    @Json(name = "common")
    val common: Boolean,
    @Json(name = "hot")
    val hot: Boolean,
    @Json(name = "icon")
    val icon: String,
    @Json(name = "phrase")
    val phrase: String,
    @Json(name = "picid")
    val picid: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "value")
    val value: String
)

@JsonClass(generateAdapter = true)
data class EmotionBeanItem(
    @Json(name = "category")
    val category: String,
    @Json(name = "common")
    val common: Boolean,
    @Json(name = "hot")
    val hot: Boolean,
    //链接
    @Json(name = "icon")
    val icon: String,
    @Json(name = "phrase")
    val phrase: String,
    @Json(name = "picid")
    val picid: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "url")
    val url: String,
    //表情名
    @Json(name = "value")
    val value: String
) {

}