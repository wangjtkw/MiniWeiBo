package com.example.miniweibo.ui.home.concern

import android.util.Log
import android.view.View
import com.example.miniweibo.ui.WebViewActivity
import com.example.miniweibo.common.DataBindingViewHolder
import com.example.miniweibo.data.bean.bean.WebViewJumpBean
import com.example.miniweibo.data.bean.entity.WebInfoEntity
import com.example.miniweibo.databinding.RvItemConcernBinding
import com.example.miniweibo.ext.isConnectedNetwork
import com.example.miniweibo.sdk.SDKUtil
import com.example.miniweibo.ui.InfoDetailActivity
import com.example.miniweibo.util.RichTextUtil
import com.example.miniweibo.util.TimeUtil
import com.example.miniweibo.util.ToastUtil
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
//                .setLink(view.context)
                .setEmotion(view.context)
                .build()
            launch(Dispatchers.Main) {
                Log.d(TAG, "执行时间 ${System.currentTimeMillis()} content：$content")
                mBinding.concernContentTv.text = content
            }
            mBinding.concernHeadImg.setOnClickListener {
                if (!view.context.isConnectedNetwork()) {
                    ToastUtil(view.context).makeToast("当前网络未连接！")
                }else{
                    Log.d(TAG,"click")
                    InfoDetailActivity.actionStart(view.context)
                }
            }
            mBinding.concernWebLayout.setOnClickListener {
                if (!view.context.isConnectedNetwork()) {
                    ToastUtil(view.context).makeToast("当前网络未连接！")
                } else {
                    val accessToken = SDKUtil.getSDKUtil().getAccessTokenBean().accessToken
                    val url =
                        "http://api.weibo.com/2/statuses/go?access_token=${accessToken}&uid=${data.userIdStr}&id=${data.idstr}"
                    val jumpBean = WebViewJumpBean(
                        url,
                        data.idstr,
                        data.userIdStr
                    )
                    WebViewActivity.actionStart(view.context, jumpBean)
                }
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