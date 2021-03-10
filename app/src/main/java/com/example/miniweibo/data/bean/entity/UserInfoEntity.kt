package com.example.miniweibo.data.bean.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.miniweibo.data.bean.bean.UserInfoBean
import com.example.miniweibo.ext.getEmptyOrDefault
import com.example.miniweibo.util.TimeUtil
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@Entity(tableName = "user_info_entity")
data class UserInfoEntity(
    //用户UID
    @PrimaryKey
    val id: String,
    //用户昵称
    @ColumnInfo(name = "screen_name")
    val screenName: String,
    //用户所在省级ID
    val province: String,
    //用户所在城市ID
    val city: String,
    //用户所在地
    val location: String,
    //用户个人描述
    val description: String,
    //头像地址(50x50)
    @ColumnInfo(name = "profile_image_url")
    val profileImageUrl: String,
    //性别，m：男、f：女、n：未知
    val gender: String,
    //粉丝数
    @ColumnInfo(name = "followers_count")
    val followersCount: String,
    //关注数
    @ColumnInfo(name = "friends_count")
    val friendsCount: String,
    //微博数
    @ColumnInfo(name = "statuses_count")
    val statusesCount: String,
    //收藏数
    @ColumnInfo(name = "favourites_count")
    val favouritesCount: String,
    //用户(注册)时间
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    //用户头像(大图，180x180)
    @ColumnInfo(name = "avatar_large")
    val avatarLarge: String,
    //用户头像(高清，原图)
    @ColumnInfo(name = "avatar_hd")
    val avatarHd: String,
) {
    override fun toString(): String {
        return "UserInfoBean(id='$id', screenName='$screenName', province='$province', city='$city', location='$location', description='$description', profileImageUrl='$profileImageUrl', gender='$gender', followersCount=$followersCount, friendsCount=$friendsCount, statusesCount=$statusesCount, favouritesCount=$favouritesCount, createdAt='$createdAt', avatarLarge='$avatarLarge', avatarHd='$avatarHd')"
    }

    companion object {
        fun convert2UserInfoEntity(userInfoBean: UserInfoBean): UserInfoEntity {
            return userInfoBean.run {
                val entityCreatedAt = TimeUtil.getTimestamp(TimeUtil.parseTime(createdAt))

                val entityDescription = if (description.isNullOrEmpty()) {
                    ""
                } else {
                    "简介:$description"
                }

                UserInfoEntity(
                    id = id,
                    screenName = screenName,
                    province = province.getEmptyOrDefault { "" },
                    city = city.getEmptyOrDefault { "" },
                    location = location.getEmptyOrDefault { "未知" },
                    description = entityDescription,
                    profileImageUrl = profileImageUrl,
                    gender = gender,
                    followersCount = "$followersCount",
                    friendsCount = "$friendsCount",
                    statusesCount = "$statusesCount",
                    favouritesCount = "$favouritesCount",
                    createdAt = entityCreatedAt,
                    avatarLarge = avatarLarge.getEmptyOrDefault { "" },
                    avatarHd = avatarHd.getEmptyOrDefault { "" }
                )
            }
        }
    }
}