package com.example.miniweibo.data.bean.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key_entity")
data class RemoteKeyEntity(
    @PrimaryKey
    val type: String,
    val nextPageKey: Int?
) {
    companion object {
        const val TYPE_CONCERN = "type_concern"
        const val TYPE_OTHER = "type_other"
    }
}