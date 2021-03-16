package com.example.miniweibo.data.db

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter

class WebInfoTypeConverter {

    @TypeConverter
    fun picUrls2Json(data: List<String>): String {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter<List<String>>(
            Types.newParameterizedType(
                List::class.java,
                String::class.java
            )
        )
        return adapter.toJson(data);
    }

    @TypeConverter
    fun json2picUrls(json: String): List<String> {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter<List<String>>(
            Types.newParameterizedType(
                List::class.java,
                String::class.java
            )
        )
        return adapter.fromJson(json) ?: mutableListOf("")
    }


}