package com.example.miniweibo.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.miniweibo.R
import com.example.miniweibo.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {
    private val TAG = "MessageFragment"

    private var binding: FragmentMessageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_message,
            container,
            false,
//            dataBindingComponent
        )
        init()
        return binding!!.root
    }

    private fun init() {
        binding!!.testImg.setImageURI("http://cdn.shibe.online/shibes/3a77a2b738c071b2b1f21e44cc1dd5c6a328c9a7.jpg")
//        setMedia()
    }

    override fun onResume() {
        super.onResume()
        setMedia()
    }

    private fun setMedia() {
        Log.d(TAG, "start")

//        mediaPlayer.setDataSource("http://vodkgeyttp8.vod.126.net/cloudmusic/ZDBkICFhMSEhMCA0YiIgZA==/mv/2424/d2c248693946d2688bc64dbada5c01e3.mp4?wsSecret=629a01636488165b33d01b8b53b309fa&wsTime=1615620761")

        binding!!.testPlayerView
    }

}