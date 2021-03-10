package com.example.miniweibo.data.bean.entity

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import androidx.room.*
import com.example.miniweibo.data.bean.bean.Statuse
import com.example.miniweibo.ext.getEmptyOrDefault
import com.example.miniweibo.util.RegExUtil
import com.example.miniweibo.util.RichTextUtil
import com.example.miniweibo.util.TimeUtil

@Entity(tableName = "web_info_entity")
data class WebInfoEntity(
    //微博id
    @PrimaryKey
    @ColumnInfo(name = "idstr")
    val idstr: String,
    //点赞数
    @ColumnInfo(name = "attitudes_count")
    val attitudesCount: String,
    //中等尺寸图片显示图片
    @ColumnInfo(name = "bmiddle_pic")
    val bmiddlePic: String?,
    //评论数
    @ColumnInfo(name = "comments_count")
    val commentsCount: String,
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
    val repostsCount: String,
    //来源
    @ColumnInfo(name = "source_title")
    val sourceTitle: String?,
    //内容链接
    @ColumnInfo(name = "source_url")
    val sourceUrl: String?,
    //文本内容
    @ColumnInfo(name = "text")
    val text: String?,
    //文本长度
    @ColumnInfo(name = "text_length")
    val textLength: Int?,
    @ColumnInfo(name = "content_url")
    val contentUrl: String,
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
    val page: Int,
    val type: String
) {


    companion object {
        private val TAG = "WebInfoEntity"

        fun convert2WebInfoEntity(statuse: Statuse, page: Int, type: String): WebInfoEntity {
            return statuse.run {
                val picList = mutableListOf<String>()

                picUrls?.forEach {
                    picList.add(it.thumbnailPic)
                }
                val entityCreatedAt = TimeUtil.getTimestamp(TimeUtil.parseTime(createdAt))
                val sourceTitle = source?.let {
                    RegExUtil.parseSourceTitle(it)
                }
                val sourceUrl = source?.let {
                    RegExUtil.parseSourceUrl(it)
                }
                var contentUrl = ""
                var content = text ?: ""
                if (text != null) {
                    val richTextUtil = RichTextUtil()
                    val addressIndexList = richTextUtil.findStr(text, "http")
                    addressIndexList.map { index ->
                        if (richTextUtil.isEndAddress(text, index)) {
                            val end =
                                richTextUtil.findAddressEndIndex(text, addressIndexList[0], ' ')
                            contentUrl =
                                text.substring(startIndex = addressIndexList[0], endIndex = end)
                            content =
                                text.substring(startIndex = 0, endIndex = addressIndexList[0] - 1)
                        }
                    }
                }
                if (content.endsWith("全文：")) {
                    content = content.substring(startIndex = 0, endIndex = content.length - 1)
                }


                Log.d(TAG, "contentUrl:$contentUrl")



                WebInfoEntity(
                    idstr = idstr,
                    attitudesCount = "$attitudesCount",
                    bmiddlePic = bmiddlePic?.getEmptyOrDefault { "" },
                    commentsCount = "$commentsCount",
                    createdAt = entityCreatedAt,
                    favorited = favorited,
                    isLongText = isLongText,
                    isVote = isVote,
                    mid = mid?.getEmptyOrDefault { "" },
                    originalPic = originalPic?.getEmptyOrDefault { "" },
                    picNum = picNum,
                    picUrls = picList,
                    repostsCount = "$repostsCount",
                    sourceTitle = sourceTitle,
                    sourceUrl = sourceUrl,
                    text = content,
                    textLength = textLength,
                    thumbnailPic = thumbnailPic,
                    userIdStr = user?.idstr ?: "",
                    avatarHd = user?.avatarHd ?: "",
                    name = user?.name ?: "",
                    page = page,
                    contentUrl = contentUrl,
                    type = type
                )
            }
        }

        val diffCallback = object : DiffUtil.ItemCallback<WebInfoEntity>() {
            override fun areItemsTheSame(
                oldItem: WebInfoEntity,
                newItem: WebInfoEntity
            ): Boolean =
                oldItem.idstr == newItem.idstr

            override fun areContentsTheSame(
                oldItem: WebInfoEntity,
                newItem: WebInfoEntity
            ): Boolean =
                oldItem == newItem
        }
    }

    override fun toString(): String {
        return "WebInfoEntity(idstr='$idstr', attitudesCount=$attitudesCount, bmiddlePic=$bmiddlePic, commentsCount=$commentsCount, createdAt=$createdAt, favorited=$favorited, isLongText=$isLongText, isVote=$isVote, mid=$mid, originalPic=$originalPic, picNum=$picNum, picUrls=$picUrls, repostsCount=$repostsCount, sourceTitle=$sourceTitle, sourceUrl=$sourceUrl, text=$text, textLength=$textLength, thumbnailPic=$thumbnailPic, userIdStr=$userIdStr, avatarHd=$avatarHd, name=$name, page=$page)"
    }
}