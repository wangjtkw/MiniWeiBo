package com.example.miniweibo.ui.imgviewer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.miniweibo.R

class ImgViewerFragment : Fragment() {
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = arguments?.getString(IMG_VIEWER_URL) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_img_viewer, container, false)
    }

    companion object {

        const val IMG_VIEWER_URL = "img_viewer_url"

        @JvmStatic
        fun newInstance(url: String) =
            ImgViewerFragment().apply {
                arguments = Bundle().apply {
                    putString(IMG_VIEWER_URL, url)
                }
            }
    }
}