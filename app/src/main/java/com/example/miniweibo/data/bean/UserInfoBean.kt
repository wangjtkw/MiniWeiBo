package com.example.miniweibo.data.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
@Entity(tableName = "user_info_bean")
data class UserInfoBean(
    //用户UID
    @PrimaryKey
    val id: String,
    //用户昵称
    @Json(name = "screen_name")
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
    @Json(name = "profile_image_url")
    @ColumnInfo(name = "profile_image_url")
    val profileImageUrl: String,
    //性别，m：男、f：女、n：未知
    val gender: String,
    //粉丝数
    @Json(name = "followers_count")
    @ColumnInfo(name = "followers_count")
    val followersCount: Int,
    //关注数
    @Json(name = "friends_count")
    @ColumnInfo(name = "friends_count")
    val friendsCount: Int,
    //微博数
    @Json(name = "statuses_count")
    @ColumnInfo(name = "statuses_count")
    val statusesCount: Int,
    //收藏数
    @Json(name = "favourites_count")
    @ColumnInfo(name = "favourites_count")
    val favouritesCount: Int,
    //用户(注册)时间
    @Json(name = "created_at")
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    //用户头像(大图，180x180)
    @Json(name = "avatar_large")
    @ColumnInfo(name = "avatar_large")
    val avatarLarge: String,
    //用户头像(高清，原图)
    @Json(name = "avatar_hd")
    @ColumnInfo(name = "avatar_hd")
    val avatarHd: String,
) {
    override fun toString(): String {
        return "UserInfoBean(id='$id', screenName='$screenName', province='$province', city='$city', location='$location', description='$description', profileImageUrl='$profileImageUrl', gender='$gender', followersCount=$followersCount, friendsCount=$friendsCount, statusesCount=$statusesCount, favouritesCount=$favouritesCount, createdAt='$createdAt', avatarLarge='$avatarLarge', avatarHd='$avatarHd')"
    }
}