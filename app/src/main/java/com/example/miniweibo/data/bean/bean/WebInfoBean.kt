package com.example.miniweibo.data.bean.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WebInfoBean(
    //未知
    val hasvisible: Boolean,
    //未知
    val interval: Int?,
    //总数
    @Json(name = "total_number")
    val totalNumber: Int,
    //请求时返回小于等于
    @Json(name = "max_id_str")
    val maxIdStr: String?,
    @Json(name = "statuses")
    val statuses: List<WebStatuse>?,
    //是否有未读
    @Json(name = "has_unread")
    val hasUnread: Int?,
)








