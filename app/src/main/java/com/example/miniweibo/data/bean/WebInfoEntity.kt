package com.example.miniweibo.data.bean

import androidx.room.*
import com.example.miniweibo.ext.getEmptyOrDefault
import com.example.miniweibo.util.RegExUtil
import com.example.miniweibo.util.TimeUtil
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

@Entity(tableName = "web_info_entity")
data class WebInfoEntity(
    //微博id
    @PrimaryKey
    @ColumnInfo(name = "idstr")
    val idstr: String,
    //点赞数
    @ColumnInfo(name = "attitudes_count")
    val attitudesCount: Int?,
    //中等尺寸图片显示图片
    @ColumnInfo(name = "bmiddle_pic")
    val bmiddlePic: String?,
    //评论数
    @ColumnInfo(name = "comments_count")
    val commentsCount: Int?,
    //创建时间(时间戳)
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    //是否收藏
    @ColumnInfo(name = "favorited")
    val favorited: Boolean?,
    //长文显示“全文”
    @ColumnInfo(name = "isLongText")
    val isLongText: Boolean?,
    //投票
    @ColumnInfo(name = "is_vote")
    val isVote: Int?,
    //微博的mid
    @ColumnInfo(name = "mid")
    val mid: String?,
    //原图
    @ColumnInfo(name = "original_pic")
    val originalPic: String?,
    //图片数量
    @ColumnInfo(name = "pic_num")
    val picNum: Int?,
    @ColumnInfo(name = "pic_urls")
    val picUrls: List<String>?,
    //转发数
    @ColumnInfo(name = "reposts_count")
    val repostsCount: Int?,
    //来源
    @ColumnInfo(name = "source_title")
    val sourceTitle: String?,
    @ColumnInfo(name = "source_url")
    val sourceUrl: String?,
    //文本内容
    @ColumnInfo(name = "text")
    val text: String?,
    //文本长度
    @ColumnInfo(name = "textLength")
    val textLength: Int?,
    //缩略图
    @ColumnInfo(name = "thumbnail_pic")
    val thumbnailPic: String?,
    @ColumnInfo(name = "user_id_str")
    val userIdStr: String?,
    //头像
    @ColumnInfo(name = "avatar_hd")
    val avatarHd: String?,
    //名称
    @ColumnInfo(name = "name")
    val name: String?,
    val page: Int
) {
    companion object {
        fun convert2WebInfoEntity(statuse: Statuse, page: Int): WebInfoEntity {
            return statuse.run {
                val picList = mutableListOf<String>()

                picUrls?.forEach {
                    picList.add(it.thumbnailPic)
                }
                val entityCreatedAt = createdAt?.let {
                    TimeUtil.run {
                        getTimestamp(parseTime(it))
                    }
                }
                val sourceTitle = source?.let {
                    RegExUtil.parseSourceTitle(it)
                }
                val sourceUrl = source?.let {
                    RegExUtil.parseSourceUrl(it)
                }

                WebInfoEntity(
                    idstr = idstr,
                    attitudesCount = attitudesCount,
                    bmiddlePic = bmiddlePic?.getEmptyOrDefault { "" },
                    commentsCount = commentsCount,
                    createdAt = entityCreatedAt,
                    favorited = favorited,
                    isLongText = isLongText,
                    isVote = isVote,
                    mid = mid?.getEmptyOrDefault { "" },
                    originalPic = originalPic?.getEmptyOrDefault { "" },
                    picNum = picNum,
                    picUrls = picList,
                    repostsCount = repostsCount,
                    sourceTitle = sourceTitle,
                    sourceUrl = sourceUrl,
                    text = text?.getEmptyOrDefault { "" },
                    textLength = textLength,
                    thumbnailPic = thumbnailPic,
                    userIdStr = user?.idstr ?: "",
                    avatarHd = user?.avatarHd ?: "",
                    name = user?.name ?: "",
                    page = page
                )
            }
        }
    }
}