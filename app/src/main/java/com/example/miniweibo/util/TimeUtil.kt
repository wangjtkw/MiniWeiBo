package com.example.miniweibo.util

import java.text.DateFormat
import java.util.*

object TimeUtil {
    const val MINUTE_DIFFER = 60
    const val HOUR_DIFFER = 3600
    const val DAY_DIFFER = 86_400
    const val TWO_DAY_DIFFER = 172_800

    /**
     * 将请求来的时间转为yyyy-MM-dd hh:mm:ss的形式
     * @param time ：网络请求来的时间
     * @return yyyy-MM-dd hh:mm:ss格式时间
     */
    fun parseTime(time: String): String {
        val strs = time.trim().split(" ")
        if (strs.size != 6) {
            throw Exception("time format error")
        }
        val month = strs[1].let {
            when (it) {
                "Jan" -> "01"
                "Feb" -> "02"
                "Mar" -> "03"
                "Apr" -> "04"
                "May" -> "05"
                "Jun" -> "06"
                "Jul" -> "07"
                "Aug" -> "08"
                "Sept" -> "09"
                "Oct" -> "10"
                "Nov" -> "11"
                "Dec" -> "12"
                else -> throw Exception("time format error")
            }
        }
        val day = strs[2].let {
            val i = it.toInt()
            when {
                i < 10 -> "0$i"
                else -> "$i"
            }
        }
        val _time = strs[3]
        val year = strs[5]

        return "$year-$month-$day $_time"
    }

    /**
     * 根据yyyy-MM-dd hh:mm:ss 格式时间获取时间戳
     * @param parsedTime：yyyy-MM-dd hh:mm:ss 时间
     * @return 传入时间的时间戳
     */
    fun getTimestamp(parsedTime: String): Long {
        val locale = Locale("zh", "CN")
        val date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, locale)
            .parse(parsedTime)
        return (date?.time ?: 0) / 1000
    }

    /**
     * 传入时间戳与当前时间的差距
     * @param oldTimestamp：历史时间
     * @return 差距代表
     */
    fun getTimeDifference(oldTimestamp: Long): String {
        val newTimestamp = Date().time / 1000
        val differ = newTimestamp - oldTimestamp
        return when {
            differ > TWO_DAY_DIFFER -> {
                val month = getDateByTimestamp(oldTimestamp).get(Calendar.MONTH)
                val day = getDateByTimestamp(oldTimestamp).get(Calendar.DAY_OF_MONTH)
                "$month-$day"
            }
            differ < TWO_DAY_DIFFER -> {
                val hour = getDateByTimestamp(oldTimestamp).get(Calendar.HOUR_OF_DAY)
                val minute = getDateByTimestamp(oldTimestamp).get(Calendar.MINUTE)
                val hourStr = if (hour < 10) {
                    "0$hour"
                } else {
                    "$hour"
                }
                "昨天$hourStr:$minute"
            }
            differ < DAY_DIFFER -> "${differ / DAY_DIFFER}小时前"
            differ < HOUR_DIFFER -> "${differ / MINUTE_DIFFER}分钟前"
            differ < MINUTE_DIFFER -> "刚刚"
            else -> ""
        }
    }

    /**
     * @param timestamp：时间戳（秒）
     * @return 对应时间戳的[Calendar]
     */
    fun getDateByTimestamp(timestamp: Long): Calendar {
        val date = Date(timestamp * 1000)
        val locale = Locale("zh", "CN")
        val calendar = Calendar.getInstance(locale)
        calendar.time = date
        return calendar
    }

}