package com.example.miniweibo.ui.imgviewer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.miniweibo.R
import com.example.miniweibo.databinding.ActivityImgViewerBinding
import com.example.miniweibo.ui.WebViewActivity

class ImgViewerActivity : AppCompatActivity() {

    private var binding: ActivityImgViewerBinding? = null

    private var imgUrlList: ArrayList<String>? = null

    private var currentIndex: Int? = 0

    private var mAdapter: ImagePagerAdapter? = null

    private var temp = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_img_viewer
            ) as ActivityImgViewerBinding
        getParam()
        init()
    }

    private fun getParam() {
        imgUrlList = intent.getStringArrayListExtra(IMG_VIEWER_PARAM_LIST) ?: ArrayList()
        currentIndex = intent.getIntExtra(IMG_VIEWER_PARAM_INDEX, 0)
        temp = "/${imgUrlList!!.size}"
    }

    fun init() {
        var text = "${(currentIndex ?: 0) + 1}${temp}"
        binding!!.imgViewerIndicatorTv.text = text
        require(!imgUrlList.isNullOrEmpty()) { "imgUrlList must not be null" }
        binding!!.imgViewerPager.run {
            mAdapter = ImagePagerAdapter(supportFragmentManager, imgUrlList!!, this)
            adapter = mAdapter
            currentItem = currentIndex ?: 0
            addOnPageChangeListener(object :
                ViewPager.OnPageChangeListener {
                override fun onPageSelected(position: Int) {
                    text = "${position + 1}${temp}"
                    binding!!.imgViewerIndicatorTv.text = text
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageScrollStateChanged(state: Int) {
                }
            })
        }
    }


    companion object {
        const val IMG_VIEWER_PARAM_LIST = "img_viewer_param_list"
        const val IMG_VIEWER_PARAM_INDEX = "img_viewer_param_index"

        fun actionStart(context: Context, list: ArrayList<String>, index: Int) {
            val intent = Intent(context, ImgViewerActivity::class.java)
            intent.putStringArrayListExtra(IMG_VIEWER_PARAM_LIST, list)
            intent.putExtra(IMG_VIEWER_PARAM_INDEX, index)
            context.startActivity(intent)
        }
    }
}

