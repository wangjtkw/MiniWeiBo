package com.example.miniweibo.ui.home.concern

import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.miniweibo.common.DataBindingViewHolder
import com.example.miniweibo.data.bean.bean.WebViewJumpBean
import com.example.miniweibo.data.bean.entity.WebInfoEntity
import com.example.miniweibo.databinding.RvItemConcernBinding
import com.example.miniweibo.ext.isConnectedNetwork
import com.example.miniweibo.sdk.SDKUtil
import com.example.miniweibo.ui.InfoDetailActivity
import com.example.miniweibo.ui.WebViewActivity
import com.example.miniweibo.util.RichTextUtil
import com.example.miniweibo.util.TimeUtil
import com.example.miniweibo.util.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ConcernViewHolder(view: View) :
    DataBindingViewHolder<WebInfoEntity>(view) {

    private val mBinding: RvItemConcernBinding = DataBindingUtil.bind(view)!!
    private val TAG = "ConcernViewHolder"

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
                .setEmotion(context())
                .build()
            launch(Dispatchers.Main) {
//                Log.d(TAG, "content：$content")
                mBinding.concernContentTv.text = content
            }
        }
        mBinding.concernHeadImg.setOnClickListener {
            if (!view.context.isConnectedNetwork()) {
                ToastUtil(context()).makeToast("当前网络未连接！")
            } else {
                Log.d(TAG, "click")
                InfoDetailActivity.actionStart(context())
            }
        }
        mBinding.concernContentTv.setOnClickListener {

            if (!view.context.isConnectedNetwork()) {
                ToastUtil(context()).makeToast("当前网络未连接！")
            } else {
                val accessToken = SDKUtil.getSDKUtil().getAccessTokenBean().accessToken
                val url =
                    "http://api.weibo.com/2/statuses/go?access_token=${accessToken}&uid=${data.userIdStr}&id=${data.idstr}"
                val jumpBean = WebViewJumpBean(
                    url,
                    data.idstr,
                    data.userIdStr
                )
                WebViewActivity.actionStart(context(), jumpBean)
            }
        }

        Log.d(TAG, "entity:${data}")
//        setImg(data)
        initRV(data)

    }

//    fun setImg(data: WebInfoEntity) {
//        if (data.picNum ?: 0 > 1) {
////            Log.d(TAG, "setImg run")
//            Log.d(TAG, "picNum:${data.picNum.toString()} data:${data.text}")
////            Log.d(TAG, "picUrl:${data.bmiddlePicUrls!![0]}")
//            data.originaPicUrls?.map {
//                Log.d(TAG, "url:$it")
//            }
//
//            mBinding.concernSingleImgFl.visibility = View.VISIBLE
//            mBinding.concernShowImg.visibility = View.VISIBLE
//            mBinding.concernShowImg.setImageURI(Uri.parse(data.bmiddlePicUrls!![0]))
//        }
//    }

    fun initRV(data: WebInfoEntity) {
        if (data.picNum == null || data.picNum <= 0 || data.bmiddlePicUrls.isNullOrEmpty()) {
            mBinding.concernShowImgRv.visibility = View.GONE
            return
        }
        val mAdapter = ImgAdapter()

        mBinding.concernShowImgRv.run {
            visibility = View.VISIBLE
            adapter = mAdapter
            layoutManager = GridLayoutManager(context(), 3)
        }
        mAdapter.addDataList(data.bmiddlePicUrls)
    }
}

