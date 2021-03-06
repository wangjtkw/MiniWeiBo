package com.example.miniweibo.ui.home.concern

import android.util.Log
import android.view.View
import com.example.miniweibo.common.DataBindingViewHolder
import com.example.miniweibo.data.bean.entity.WebInfoEntity
import com.example.miniweibo.databinding.RvItemConcernBinding
import com.example.miniweibo.util.RichTextUtil
import com.example.miniweibo.util.TimeUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ConcernViewHolder(view: View) : DataBindingViewHolder<WebInfoEntity>(view) {
    private val TAG = "ConcernViewHolder"

    private val mBinding: RvItemConcernBinding by viewHolderBinding(view)
    override fun bindData(data: WebInfoEntity, position: Int) {
        mBinding.webInfo = data
        mBinding.concernTimeTv.text = TimeUtil.getTimeDifference(data.createdAt)
        GlobalScope.launch {
            val content = RichTextUtil()
                .init(data.text!!)
                .setSharp()
                .setAt()
                .setAllContent()
                .setEmotion(view.context)
                .build()
            launch(Dispatchers.Main) {
                Log.d(TAG, "执行时间 ${System.currentTimeMillis()} content：$content")
                mBinding.concernContentTv.text = content
            }

//            content.setEmotion(view.context) { spannableStringBuilder ->
//                launch(Dispatchers.Main) {
//                    Log.d(TAG, "执行时间 ${System.currentTimeMillis()}")
//                    mBinding.concernContentTv.text = spannableStringBuilder
//                }
//            }
        }


//        Html.fromHtml()
//        FROM_HTML_MODE_COMPACT：html块元素之间使用一个换行符分隔
//        FROM_HTML_MODE_LEGACY：html块元素之间使用两个换行符分隔
    }
}