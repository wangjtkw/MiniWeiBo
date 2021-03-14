package com.example.miniweibo.media

interface MediaPlayerStateListener {

    fun onPrepared()

    fun onCompleted()

    fun onError(what: Int, extra: Int)

}