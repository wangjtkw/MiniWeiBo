package com.example.miniweibo.ui.imgviewer

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.miniweibo.R
import com.example.miniweibo.myview.ImgViewer
import com.example.miniweibo.myview.OnChildMovingListener

class ImgViewerFragment : Fragment() {
    private var url: String? = null

    private var imgViewer: ImgViewer? = null

    private val TAG = "ImgViewerFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = arguments?.getString(IMG_VIEWER_URL) ?: ""
        Log.d(TAG, "url: $url")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_img_viewer, container, false)

        return view
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
        imgViewer!!.setImg(R.drawable.bg_img)
        imgViewer!!.setImg(url!!)
        require(mOnChildMovingListener != null) { "OnChildMovingListener is not be null" }
        imgViewer!!.setOnMovingListener(mOnChildMovingListener!!)
    }


    companion object {

        const val IMG_VIEWER_URL = "img_viewer_url"

        private var mOnChildMovingListener: OnChildMovingListener? = null

        @JvmStatic
        fun newInstance(
            url: String,
            onChildMovingListener: OnChildMovingListener
        ): ImgViewerFragment {
            mOnChildMovingListener = onChildMovingListener
            return ImgViewerFragment().apply {
                arguments = Bundle().apply {
                    putString(IMG_VIEWER_URL, url)
                }
            }
        }

    }
}