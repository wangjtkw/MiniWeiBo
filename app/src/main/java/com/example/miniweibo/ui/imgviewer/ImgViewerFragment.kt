package com.example.miniweibo.ui.imgviewer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.miniweibo.R
import com.example.miniweibo.data.bean.bean.ImgWrapBean
import com.example.miniweibo.ext.isConnectedNetwork
import com.example.miniweibo.myview.ImgViewer
import com.example.miniweibo.myview.OnChildMovingListener
import com.example.miniweibo.util.ToastUtil

class ImgViewerFragment : Fragment() {
    private var url: ImgWrapBean? = null

    private var imgViewer: ImgViewer? = null

    private val TAG = "ImgViewerFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = arguments?.getParcelable(IMG_VIEWER_URL)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_img_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        imgViewer = view?.findViewById(R.id.img_viewer_viewer)
        if (imgViewer == null) {
            Log.d(TAG, "viewer is null")
        }
        if (!requireContext().isConnectedNetwork()) {
            ToastUtil.makeToast("当前网络未连接！")
        }
        imgViewer!!.setImg(R.drawable.ic_loading)
        imgViewer!!.setImg(url?.url)
        imgViewer!!.setImg(url?.originUrl)
        require(mOnChildMovingListener != null) { "OnChildMovingListener is not be null" }
        imgViewer!!.setOnMovingListener(mOnChildMovingListener!!)
    }


    companion object {

        const val IMG_VIEWER_URL = "img_viewer_url"

        private var mOnChildMovingListener: OnChildMovingListener? = null

        @JvmStatic
        fun newInstance(
            url: ImgWrapBean,
            onChildMovingListener: OnChildMovingListener
        ): ImgViewerFragment {
            mOnChildMovingListener = onChildMovingListener
            return ImgViewerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(IMG_VIEWER_URL, url)
                }
            }
        }

    }
}