package com.example.miniweibo.ui.home.concern

import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.util.Log
import android.view.View
import com.example.miniweibo.common.DataBindingViewHolder
import com.example.miniweibo.data.bean.WebInfoEntity
import com.example.miniweibo.databinding.RvItemConcernBinding
import com.example.miniweibo.util.RichTextUtil
import com.example.miniweibo.util.TimeUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ConcernViewHolder(view: View) : DataBindingViewHolder<WebInfoEntity>(view) {
    private val TAG = "ConcernViewHolder"

    private val mBinding: RvItemConcernBinding by viewHolderBinding(view)
    override fun bindData(data: WebInfoEntity, position: Int) {
        mBinding.webInfo = data
        mBinding.concernTimeTv.text = TimeUtil.getTimeDifference(data.createdAt)
        val content = RichTextUtil()
            .init(data.text!!)
            .setSharp()
            .setAt()
            .setAllContent()
        mBinding.concernContentTv.text = content.build()

//        Html.fromHtml()
//        FROM_HTML_MODE_COMPACT：html块元素之间使用一个换行符分隔
//        FROM_HTML_MODE_LEGACY：html块元素之间使用两个换行符分隔
    }
}