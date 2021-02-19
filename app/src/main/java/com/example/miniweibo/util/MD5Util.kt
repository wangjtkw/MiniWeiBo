package com.example.miniweibo.util

object MD5Util {

    private val mD5UtilNative = MD5UtilNative()

    fun getMd5(str: String): String = mD5UtilNative.md5FromJNI(str)
}

class MD5UtilNative {

    external fun md5FromJNI(string: String): String

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("md5-lib")
        }
    }
}