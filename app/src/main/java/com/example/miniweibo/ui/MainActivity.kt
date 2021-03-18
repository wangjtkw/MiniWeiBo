package com.example.miniweibo.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.paging.ExperimentalPagingApi
import androidx.viewpager2.widget.ViewPager2
import com.example.miniweibo.R
import com.example.miniweibo.common.ViewPagerAdapter
import com.example.miniweibo.databinding.ActivityMainBinding
import com.example.miniweibo.ui.home.HomeFragment
import com.example.miniweibo.ui.mine.MineFragment
import com.example.miniweibo.ui.video.VideoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


@ExperimentalPagingApi
class MainActivity : AppCompatActivity(), HasAndroidInjector {
    val TAG = "MainActivity"

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    private var binding: ActivityMainBinding? = null

//    val tabSelectIc = listOf(
//        R.drawable.ic_home_select,
//        R.drawable.ic_message_select,
//        R.drawable.ic_vedio_select,
//        R.drawable.ic_mine_select
//    )
//
//    val tabUnSelectIc = listOf(
//        R.drawable.ic_home,
//        R.drawable.ic_message,
//        R.drawable.ic_video,
//        R.drawable.ic_mine
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
        init()
    }

    private fun init() {
        initViewPager()
        binding?.apply { bind(mainNavigationView, mainVp) }
    }

    private fun initViewPager() {
        val fragmentList = listOf(
            HomeFragment(),
            MessageFragment(),
            VideoFragment(),
            MineFragment.newInstance()
        )
        val mAdapter = ViewPagerAdapter(fragmentList, this)
        binding!!.mainVp.apply {
            adapter = mAdapter
            currentItem = 0
        }
    }

    private fun bind(navigationView: BottomNavigationView, viewPager2: ViewPager2) {
        navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> viewPager2.currentItem = 0
                R.id.action_message -> viewPager2.currentItem = 1
                R.id.action_video -> viewPager2.currentItem = 2
                R.id.action_mine -> viewPager2.currentItem = 3
            }

            true
        }
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val item: MenuItem = navigationView.menu.getItem(position)
                item.isChecked = true
            }
        })
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}