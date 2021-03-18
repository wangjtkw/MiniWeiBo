package com.example.miniweibo.ui.imgviewer

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.miniweibo.myview.OnChildMovingListener

@Suppress("DEPRECATION")
class ImagePagerAdapter(
    fragmentManager: FragmentManager,
    private val mImgUrlList: ArrayList<String>,
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