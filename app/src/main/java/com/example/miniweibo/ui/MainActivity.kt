package com.example.miniweibo.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.miniweibo.R
import com.example.miniweibo.common.TabPagerBinding
import com.example.miniweibo.common.ViewPagerAdapter
import com.example.miniweibo.databinding.ActivityMainBinding
import com.example.miniweibo.sdk.SDKUtil
import com.example.miniweibo.util.ToastUtil
import com.google.android.material.tabs.TabLayout
import dagger.android.AndroidInjection


class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    private var binding: ActivityMainBinding? = null

    val tabSelectIc = listOf(
        R.drawable.ic_home_select,
        R.drawable.ic_message_select,
        R.drawable.ic_vedio_select,
        R.drawable.ic_mine_select
    )

    val tabUnSelectIc = listOf(
        R.drawable.ic_home,
        R.drawable.ic_message,
        R.drawable.ic_video,
        R.drawable.ic_mine
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
        init()
    }

    private fun init() {
        initViewPager()
        binding?.apply { bind(mainTabLayout, mainVp) }
    }

    private fun initViewPager() {
        val fragmentList = listOf(
            HomeFragment.newInstance("1", "2"),
            MessageFragment.newInstance("1", "2"),
            VedioFragment.newInstance("1", "2"),
            MineFragment.newInstance()
        )
        val mAdapter = ViewPagerAdapter(fragmentList, this)
        binding!!.mainVp.apply {
            adapter = mAdapter
            currentItem = 0
        }
    }

    private fun bind(tabLayout: TabLayout, viewPager2: ViewPager2) {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager2.currentItem = tab?.position ?: 0
                tab?.apply {
                    icon = ContextCompat.getDrawable(this@MainActivity, tabSelectIc[position])
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.apply {
                    icon = ContextCompat.getDrawable(this@MainActivity, tabUnSelectIc[position])
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.select()
            }
        })
    }


}