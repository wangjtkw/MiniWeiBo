package com.example.miniweibo.data.bean.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "idstr")
    val idstr: String?,
    //头像
    @Json(name = "avatar_hd")
    val avatarHd: String?,
    //名称
    @Json(name = "name")
    val name: String?
)