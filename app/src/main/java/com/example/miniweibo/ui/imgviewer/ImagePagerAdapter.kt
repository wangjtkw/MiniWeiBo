package com.example.miniweibo.ui.imgviewer

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.miniweibo.data.bean.bean.ImgWrapBean
import com.example.miniweibo.myview.OnChildMovingListener

@Suppress("DEPRECATION")
class ImagePagerAdapter(
    fragmentManager: FragmentManager,
    private val mImgUrlList: ArrayList<ImgWrapBean>,
    private val onChildMovingListener: OnChildMovingListener
) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return mImgUrlList.size
    }

    override fun getItem(position: Int): Fragment {
        return ImgViewerFragment.newInstance(mImgUrlList[position], onChildMovingListener)
    }
}