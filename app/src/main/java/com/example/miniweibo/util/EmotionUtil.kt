package com.example.miniweibo.util

import android.util.Log
import com.example.miniweibo.data.db.EmotionDao
import com.example.miniweibo.data.db.MiniWeiBoDb
import com.example.miniweibo.di.DaggerAppComponent
import javax.inject.Inject

class EmotionUtil @Inject constructor(private val emotionDao: EmotionDao) {

    private val TAG = "EmotionUtil"

    suspend fun getEmotionUrl(emotionName: String): String {
        val emotionEntity = emotionDao.selectByName(emotionName)
        return emotionEntity.icon
    }


}