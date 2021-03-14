package com.example.miniweibo.media

interface MainThreadMediaPlayerListener {

    fun onVideoSizeChangedMainThread(width: Int, height: Int)

    fun onVideoPreparedMainThread()

    fun onVideoCompletionMainThread()

    fun onErrorMainThread(what: Int, extra: Int)

    fun onBufferingUpdateMainThread(percent: Int)

    fun onVideoStoppedMainThread()
}