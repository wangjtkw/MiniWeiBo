package com.example.miniweibo.ui.video

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
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
        listOf<String>(
            "http://vodkgeyttp8.vod.126.net/cloudmusic/obj/w5zDkcKQw6LDiWzDgcK2/5002502198/4738/3d15/27ac/f47aeaedd94a2118402964f482bdd1d1.mp4?wsSecret=129c40c6d5634639834f69c77899452b&wsTime=1615639716",
            "http://vodkgeyttp8.vod.126.net/cloudmusic/0hlxWfMa_3122336150_shd.mp4?wsSecret=07b397a56e73aa8fee7d1532409fbed2&wsTime=1615639945",
            "http://vodkgeyttp8.vod.126.net/cloudmusic/obj/w5zDkcKQw6LDiWzDgcK2/7127093712/4e8b/9d78/587e/8d81f0b5e5d49b5464bb645c95765615.mp4?wsSecret=95ddb1f856b01da2248378d0017135cc&wsTime=1615639987"
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

    fun init() {
        mediaPlayer = MediaPlayer()
        initRV()
        initSwipeRefresh()
        requestData()
    }

    fun initRV() {
        mAdapter = VideoAdapter(mediaPlayer!!)
        val mLayoutManager = GridLayoutManager(requireContext(), 2)
        binding!!.videoRv.run {
            layoutManager = mLayoutManager
            adapter = mAdapter
            addOnScrollListener(MyMediaController(mLayoutManager,mediaPlayer!!))
        }
    }

    fun initSwipeRefresh() {
        binding!!.videoSwipeRefresh.setOnRefreshListener {
            requestData()
        }
    }

    fun requestData() {
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


