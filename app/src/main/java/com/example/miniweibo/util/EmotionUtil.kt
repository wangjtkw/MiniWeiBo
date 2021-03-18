package com.example.miniweibo.util

import com.example.miniweibo.data.db.EmotionDao
import javax.inject.Inject

class EmotionUtil @Inject constructor(private val emotionDao: EmotionDao) {

    private val TAG = "EmotionUtil"

    fun getEmotionUrl(emotionName: String): String {
        val emotionEntity = emotionDao.selectByName(emotionName)
        return emotionEntity.icon
    }


}