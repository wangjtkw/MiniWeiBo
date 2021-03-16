package com.example.miniweibo.ui.imgviewer

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ImagePagerAdapter(activity: FragmentActivity, private val mImgUrlList: ArrayList<String>) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return mImgUrlList.size
    }

    override fun createFragment(position: Int): Fragment {
        return ImgViewerFragment.newInstance(mImgUrlList[position])
    }
}