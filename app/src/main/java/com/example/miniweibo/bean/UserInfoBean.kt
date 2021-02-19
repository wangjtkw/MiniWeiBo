package com.example.miniweibo.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info_bean")
data class UserInfoBean(
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
    val followersCount: Int,
    //关注数
    @ColumnInfo(name = "friends_count")
    val friendsCount: Int,
    //微博数
    @ColumnInfo(name = "statuses_count")
    val statusesCount: Int,
    //收藏数
    @ColumnInfo(name = "favourites_count")
    val favouritesCount: Int,
    //用户(注册)时间
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    //用户头像(大图，180x180)
    @ColumnInfo(name = "avatar_large")
    val avatarLarge: String,
    //用户头像(高清，原图)
    @ColumnInfo(name = "avatar_hd")
    val avatarHd: String,
)