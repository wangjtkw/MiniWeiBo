package com.example.miniweibo.ui.video

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.miniweibo.R
import com.example.miniweibo.data.bean.Resource
import com.example.miniweibo.data.bean.Status
import com.example.miniweibo.data.bean.entity.ImgEntity
import com.example.miniweibo.databinding.FragmentVedioBinding
import com.example.miniweibo.media.MyMediaController
import com.example.miniweibo.ui.home.concern.MyOnItemTouchListener
import com.example.miniweibo.util.ToastUtil
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class VideoFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var binding: FragmentVedioBinding? = null

    private val videoViewModel: VideoViewModel by viewModels { viewModelFactory }

    private var mAdapter: VideoAdapter? = null

    private var mediaPlayer: MediaPlayer? = null

    private val videoList =
        listOf(
            "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
//            "https://media.w3.org/2010/05/sintel/trailer.mp4",
//            "https://www.w3schools.com/html/movie.mp4"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
//        VideoView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_vedio,
            container,
            false,
//            dataBindingComponent
        )
        init()
        return binding?.root
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer!!.reset()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer!!.release()
    }

    fun init() {
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.isLooping = true
        initRV()
        initSwipeRefresh()
        requestData()
    }

    private fun initRV() {
        mAdapter = VideoAdapter(mediaPlayer!!)
        val mLayoutManager = GridLayoutManager(requireContext(), 2)
        binding!!.videoRv.run {
            layoutManager = mLayoutManager
            adapter = mAdapter
            addOnScrollListener(MyMediaController(mLayoutManager, mediaPlayer!!))
        }
    }

    private fun initSwipeRefresh() {
        binding!!.videoSwipeRefresh.setOnRefreshListener {
            requestData()
        }
    }

    private fun requestData() {
        videoViewModel.getImg().observe(viewLifecycleOwner) {
            fetchData(it)
        }
    }

    private fun fetchData(result: Resource<List<ImgEntity>>) {
        when (result.status) {
            Status.SUCCESS -> {
                if (!result.data.isNullOrEmpty()) {
                    result.data.map {
                        it.videoUrl = getRandomVideo()
                    }
                    mAdapter!!.addDataList(result.data)
                }
            }
            Status.LOADING -> {
            }
            Status.ERROR -> {
                ToastUtil(requireContext()).makeToast("请求发生错误：${result.message ?: ""}")
            }
        }
        binding!!.videoSwipeRefresh.isRefreshing = (result.status == Status.LOADING)
    }

    private fun getRandomVideo(): String {
        val index = (videoList.indices).random()
        return videoList[index]
    }
}


