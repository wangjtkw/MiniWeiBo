package com.example.miniweibo.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.paging.ExperimentalPagingApi
import androidx.viewpager2.widget.ViewPager2
import com.example.miniweibo.R
import com.example.miniweibo.common.ViewPagerAdapter
import com.example.miniweibo.databinding.FragmentHomeBinding
import com.example.miniweibo.ui.home.concern.HomeConcernFragment
import com.example.miniweibo.ui.home.mine.HomeMineFragment
import com.google.android.material.tabs.TabLayout


@ExperimentalPagingApi
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false,
        )
        init()
        return binding?.root
    }

    private fun init() {
        require(binding != null) { "binding is null" }
        initViewPager()
        binding?.apply { bind(homeTl, homeVp) }
    }

    private fun initViewPager() {
        val fragmentList = listOf(
            HomeConcernFragment(),
            HomeMineFragment.newInstance("1", "1")
        )
        val mAdapter = ViewPagerAdapter(fragmentList, requireActivity())
        binding!!.homeVp.apply {
            adapter = mAdapter
            currentItem = 0
        }
    }

    private fun bind(tabLayout: TabLayout, viewPager2: ViewPager2) {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager2.currentItem = tab?.position ?: 0
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
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