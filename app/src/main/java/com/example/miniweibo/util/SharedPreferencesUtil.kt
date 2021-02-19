package com.example.miniweibo.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil private constructor(context: Context, fileName: String) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    fun put(key: String, any: Any) {
        val editor = sharedPreferences.edit()
        when (any) {
            is String -> editor.putString(key, any)
            is Int -> editor.putInt(key, any)
            is Boolean -> editor.putBoolean(key, any)
            is Long -> editor.putLong(key, any)
            is Float -> editor.putFloat(key, any)
            else -> editor.putString(key, any.toString())
        }
        editor.apply()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String, default: T): T {
        return when (default) {
            is String -> sharedPreferences.getString(key, default)
            is Int -> sharedPreferences.getInt(key, default)
            is Boolean -> sharedPreferences.getBoolean(key, default)
            is Long -> sharedPreferences.getLong(key, default)
            is Float -> sharedPreferences.getFloat(key, default)
            else -> sharedPreferences.getString(key, "")
        } as T
    }

    fun remove(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun contain(key: String): Boolean = sharedPreferences.contains(key)

    fun getAll(): MutableMap<String, *> = sharedPreferences.all

    companion object {
        private val sharedPreferencesMap = HashMap<String, SharedPreferencesUtil>()

        fun getInstances(fileName: String, context: Context): SharedPreferencesUtil {
            return if (sharedPreferencesMap.containsKey(fileName)) {
                sharedPreferencesMap[fileName]!!
            } else {
                val sharedPreferencesUtil = SharedPreferencesUtil(context, fileName)
                sharedPreferencesMap[fileName] = sharedPreferencesUtil
                sharedPreferencesUtil
            }
        }
    }

}