package com.example.miniweibo.ui.home.concern

import android.view.View
import com.example.miniweibo.common.DataBindingViewHolder
import com.example.miniweibo.databinding.RvItemConcernImgBinding

class ImgViewHolder(view: View) : DataBindingViewHolder<String>(view) {
    private val mBinding: RvItemConcernImgBinding = viewHolderBinding(view)

    override fun bindData(data: String, position: Int) {
        mBinding.url = data
    }
}