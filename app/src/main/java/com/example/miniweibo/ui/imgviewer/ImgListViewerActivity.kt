package com.example.miniweibo.ui.imgviewer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.example.miniweibo.R
import com.example.miniweibo.data.bean.bean.ImgWrapBean
import com.example.miniweibo.databinding.ActivityImgListViewerBinding

class ImgListViewerActivity : AppCompatActivity() {

    private var binding: ActivityImgListViewerBinding? = null

    private var imgUrlList: ArrayList<ImgWrapBean>? = null

    private var currentIndex: Int? = 0

    private var mAdapter: ImagePagerAdapter? = null

    private var temp = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_img_list_viewer
            ) as ActivityImgListViewerBinding
        getParam()
        init()
    }

    private fun getParam() {
        imgUrlList = intent.getParcelableArrayListExtra(IMG_VIEWER_PARAM_LIST) ?: ArrayList()
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

        fun actionStart(context: Context, list: ArrayList<ImgWrapBean>, index: Int) {
            val intent = Intent(context, ImgListViewerActivity::class.java)
            intent.putParcelableArrayListExtra(IMG_VIEWER_PARAM_LIST, list)
            intent.putExtra(IMG_VIEWER_PARAM_INDEX, index)
            context.startActivity(intent)
        }
    }
}

