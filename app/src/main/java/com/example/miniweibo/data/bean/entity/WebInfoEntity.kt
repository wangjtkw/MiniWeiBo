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
    @ColumnInfo(name = "thumbnail_pic_urls")
    val thumbnailPicUrls: List<String>?,
    @ColumnInfo(name = "bmiddle_pic_urls")
    val bmiddlePicUrls: List<String>?,
    @ColumnInfo(name = "original_pic_urls")
    val originalPicUrls: List<String>?,
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
        private const val TAG = "WebInfoEntity"

        fun convert2WebInfoEntity(statuse: Statuse, page: Int, type: String): WebInfoEntity {
            return statuse.run {
                val thumbnailPicList = mutableListOf<String>()
                val bmiddlePicList = mutableListOf<String>()
                val originalPicList = mutableListOf<String>()
                var bmiddlePicHost = ""
                var originalPicHost = ""
                if (!bmiddlePic.isNullOrEmpty()) {
                    bmiddlePicHost = findAndSubString(bmiddlePic, "bmiddle", true)
                }
                if (!originalPic.isNullOrEmpty()) {
                    originalPicHost = findAndSubString(originalPic, "large", true)
                }

                if (picNum > 0) {
                    picUrls?.forEach {
                        thumbnailPicList.add(it.thumbnailPic)
                        val picAfter = findAndSubString(it.thumbnailPic, "thumbnail", false)
                        val bmiddlePicUrl = "${bmiddlePicHost}${picAfter}"
                        val originalPicUrl = "${originalPicHost}${picAfter}"
                        Log.d(
                            TAG,
                            "bmiddlePicUrl:$bmiddlePicUrl thumbnailPic:${it.thumbnailPic} content：$text"
                        )
                        Log.d(TAG, "originalPicUrl:$originalPicUrl")
                        bmiddlePicList.add(bmiddlePicUrl)
                        originalPicList.add(originalPicUrl)
                    }
                }

                val entityCreatedAt = TimeUtil.getTimestamp(TimeUtil.parseTime(createdAt))
                val sourceTitle = source?.let {
                    RegExUtil.parseSourceTitle(it)
                }
                val sourceUrl = source?.let {
                    RegExUtil.parseSourceUrl(it)
                }
                val contentUrl = ""
                var content = text ?: ""
                if (text != null) {
                    val richTextUtil = RichTextUtil()
                    val addressIndexList = richTextUtil.findStr(text, "http", 0)

                    val totalTextIndex = richTextUtil.findStr(text, "全文", 0)
                    content = if (!totalTextIndex.isNullOrEmpty()) {
                        text.substring(0, totalTextIndex[0] + 2)
                    } else {
                        text
                    }


                    val stringBuilder = StringBuilder(content)
                    addressIndexList.map { index ->
                        if (index >= content.length) {
                            return@map
                        }
                        stringBuilder.insert(index, "!$")
                    }
                    content = stringBuilder.toString()
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
                    thumbnailPicUrls = thumbnailPicList,
                    bmiddlePicUrls = bmiddlePicList,
                    originalPicUrls = originalPicList,
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

        private fun findAndSubString(
            originStr: String,
            target: String,
            needBefore: Boolean
        ): String {
            val index = originStr.indexOf(target)
            var sub = ""
            if (index <= 0) {
                return sub
            }
            sub = if (needBefore) {
                originStr.substring(0, index + target.length)
            } else {
                originStr.substring(index + target.length, originStr.length)
            }
            return sub
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
        return "WebInfoEntity(idstr='$idstr', attitudesCount='$attitudesCount', bmiddlePic=$bmiddlePic, commentsCount='$commentsCount', createdAt=$createdAt, favorited=$favorited, isLongText=$isLongText, isVote=$isVote, mid=$mid, originalPic=$originalPic, picNum=$picNum, thumbnailPicUrls=$thumbnailPicUrls, bmiddlePicUrls=$bmiddlePicUrls, originalPicUrls=$originalPicUrls, repostsCount='$repostsCount', sourceTitle=$sourceTitle, sourceUrl=$sourceUrl, text=$text, textLength=$textLength, contentUrl='$contentUrl', thumbnailPic=$thumbnailPic, userIdStr=$userIdStr, avatarHd=$avatarHd, name=$name, page=$page, type='$type')"
    }


}