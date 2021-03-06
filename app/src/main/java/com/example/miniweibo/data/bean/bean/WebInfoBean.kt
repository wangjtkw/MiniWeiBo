package com.example.miniweibo.data.bean.bean

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WebInfoBean(
    //未知
    val hasvisible: Boolean,
    //未知
    val interval: Int,
    //总数
    @Json(name = "total_number")
    val totalNumber: Int,
    //请求时返回小于等于
    @Json(name = "max_id_str")
    val maxIdStr: String,
    @Json(name = "statuses")
    val statuses: List<Statuse>?,
    //是否有未读
    @Json(name = "has_unread")
    val hasUnread: Int,
)
@JsonClass(generateAdapter = true)
data class Statuse(
    //微博id
    @Json(name = "idstr")
    val idstr: String,
    //点赞数
    @Json(name = "attitudes_count")
    val attitudesCount: Int?,
    //中等尺寸图片显示图片
    @Json(name = "bmiddle_pic")
    val bmiddlePic: String?,
    //评论数
    @Json(name = "comments_count")
    val commentsCount: Int?,
    //创建时间
    @Json(name = "created_at")
    val createdAt: String,
    //是否收藏
    @Json(name = "favorited")
    val favorited: Boolean?,
    //长文显示“全文”
    @Json(name = "isLongText")
    val isLongText: Boolean?,
    //投票
    @Json(name = "is_vote")
    val isVote: Int?,
    //微博的mid
    @Json(name = "mid")
    val mid: String?,
    //原图
    @Json(name = "original_pic")
    val originalPic: String?,
    //图片数量
    @Json(name = "pic_num")
    val picNum: Int?,
    @Json(name = "pic_urls")
    val picUrls: List<PicUrl>?,
    //转发数
    @Json(name = "reposts_count")
    val repostsCount: Int?,
    //来源
    @Json(name = "source")
    val source: String?,
    //文本内容
    @Json(name = "text")
    val text: String?,
    //文本长度
    @Json(name = "textLength")
    val textLength: Int?,
    //缩略图
    @Json(name = "thumbnail_pic")
    val thumbnailPic: String?,
    @Json(name = "user")
    val user: User?,
)
@JsonClass(generateAdapter = true)
data class PicUrl(
    @Json(name = "thumbnail_pic")
    val thumbnailPic: String
)
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
