package com.example.miniweibo.ui.home.concern

import android.view.View
import com.example.miniweibo.common.DataBindingViewHolder
import com.example.miniweibo.databinding.RvItemConcernImgBinding
import com.example.miniweibo.ui.imgviewer.ImgViewerActivity

class ImgViewHolder(view: View) : DataBindingViewHolder<List<String>>(view) {
    private val mBinding: RvItemConcernImgBinding = viewHolderBinding(view)

    override fun bindData(data: List<String>, position: Int) {
        mBinding.url = data[position]
        mBinding.concernImgImg.setOnClickListener {
            ImgViewerActivity.actionStart(context(), data as ArrayList<String>, position)
        }
    }
}