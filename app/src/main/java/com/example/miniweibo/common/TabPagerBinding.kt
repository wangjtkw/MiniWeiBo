package com.example.miniweibo.common

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

object TabPagerBinding {

    fun bindTabPager(
        tabLayout: TabLayout,
        viewPager2: ViewPager2,
        tabCallBack: ((tab: TabLayout.Tab?) -> Unit)? = null,
        pagerCallBack: ((position: Int) -> Unit)? = null
    ) {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager2.currentItem = tab?.position ?: 0
                if (tabCallBack != null) {
                    tabCallBack(tab)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (pagerCallBack == null) {
                    tabLayout.getTabAt(position)?.select()
                } else {
                    pagerCallBack(position)
                }

            }
        })
    }
}