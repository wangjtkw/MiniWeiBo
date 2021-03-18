package com.example.miniweibo.ui.home.concern

import android.view.View
import com.example.miniweibo.common.DataBindingViewHolder
import com.example.miniweibo.data.bean.bean.ImgWrapBean
import com.example.miniweibo.databinding.RvItemConcernImgBinding
import com.example.miniweibo.ui.imgviewer.ImgListViewerActivity

class ImgViewHolder(view: View) : DataBindingViewHolder<List<ImgWrapBean>>(view) {
    private val mBinding: RvItemConcernImgBinding = viewHolderBinding(view)

    override fun bindData(data: List<ImgWrapBean>, position: Int) {
        mBinding.url = data[position].url
        mBinding.concernImgImg.setOnClickListener {
            ImgListViewerActivity.actionStart(context(), data as ArrayList<ImgWrapBean>, position)
        }
    }
}