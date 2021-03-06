package com.example.miniweibo.data.bean.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "access_token_entity")
data class AccessTokenEntity(
    @PrimaryKey
    val uid: String,
    @ColumnInfo(name = "user_name")
    val userName: String,
    @ColumnInfo(name = "access_token")
    val accessToken: String,
    @ColumnInfo(name = "expires_in")
    val expiresIn: Long,
    @ColumnInfo(name = "refresh_token")
    val refreshToken: String
) {
    override fun toString(): String {
        return "AccessTokenBean(uid='$uid', userName='$userName', accessToken='$accessToken', expiresIn=$expiresIn, refreshToken='$refreshToken')"
    }
}