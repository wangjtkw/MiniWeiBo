package com.example.miniweibo.media

import android.media.MediaPlayer
import com.example.miniweibo.media.MediaPlayerWrapper.State.*
import java.util.concurrent.atomic.AtomicReference

class MediaPlayerWrapper(private val mMediaPlayer: MediaPlayer) :
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnErrorListener,
    MediaPlayer.OnBufferingUpdateListener,
    MediaPlayer.OnInfoListener,
    MediaPlayer.OnCompletionListener {

    private val mState: AtomicReference<State> = AtomicReference()

    private var mediaPlayerStateListener: MediaPlayerStateListener? = null

    init {
        mMediaPlayer.setOnBufferingUpdateListener(this)
        mMediaPlayer.setOnInfoListener(this)
        mMediaPlayer.setOnCompletionListener(this)
        mMediaPlayer.setOnErrorListener(this)
    }

    enum class State {
        //空闲
        IDLE,

        //已初始化
        INITIALIZED,

        //正在加载
        PREPARING,

        //加载完成
        PREPARED,

        //开始
        STARTED,

        //暂停
        PAUSED,

        //只能调用prepare
        STOPPED,

        //播放完成
        PLAYBACK_COMPLETED,

        //release后
        END,
        ERROR
    }

    fun reset() {
        synchronized(mState) {
            when (mState.get()) {
                END, ERROR -> {
                    throw Exception("illegal state")
                }
                else -> {
                    mMediaPlayer.reset()
                    mState.set(IDLE)
                }
            }
        }
    }

    fun setDataSource(videoPath: String) {
        synchronized(mState) {
            when (mState.get()) {
                IDLE -> {
                    mMediaPlayer.setDataSource(videoPath)
                    mState.set(INITIALIZED)
                }
                else -> {
                    throw Exception("illegal state")
                }
            }
        }
    }

    fun prepare() {
        synchronized(mState) {
            when (mState.get()) {
                INITIALIZED, STOPPED -> {
                    mediaPlayerStateListener?.onPrepared()
                    mState.set(PREPARED)
                }
                else -> {
                    throw Exception("illegal state")
                }
            }
        }
    }

    fun start() {
        synchronized(mState) {
            when (mState.get()) {
                PREPARED, PAUSED, PLAYBACK_COMPLETED -> {
                    mMediaPlayer.start()
                    mState.set(STARTED)
                }
            }
        }
    }


    /**
     * 清除所有监听器
     */
    fun clearAll() {
        synchronized(mState) {
            mMediaPlayer.setOnVideoSizeChangedListener(null)
            mMediaPlayer.setOnCompletionListener(null)
            mMediaPlayer.setOnErrorListener(null)
            mMediaPlayer.setOnBufferingUpdateListener(null)
            mMediaPlayer.setOnInfoListener(null)
        }
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        synchronized(mState) { mState.set(ERROR) }
        return true
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {

    }

    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return false
    }

    override fun onCompletion(mp: MediaPlayer?) {
        synchronized(mState) { mState.set(PLAYBACK_COMPLETED) }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        synchronized(mState) {
            mState.set(PREPARED)
        }
    }


}