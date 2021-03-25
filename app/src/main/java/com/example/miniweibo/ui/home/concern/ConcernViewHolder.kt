package com.example.miniweibo.ui.home.concern

import android.net.Uri
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import com.example.miniweibo.common.DataBindingViewHolder
import com.example.miniweibo.data.bean.bean.ImgWrapBean
import com.example.miniweibo.data.bean.bean.WebViewJumpBean
import com.example.miniweibo.data.bean.entity.WebInfoEntity
import com.example.miniweibo.databinding.RvItemConcernBinding
import com.example.miniweibo.ext.isConnectedNetwork
import com.example.miniweibo.sdk.SDKUtil
import com.example.miniweibo.ui.WebViewActivity
import com.example.miniweibo.ui.imgviewer.ImgViewerActivity
import com.example.miniweibo.util.RichTextUtil
import com.example.miniweibo.util.TimeUtil
import com.example.miniweibo.util.ToastUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class ConcernViewHolder(view: View) :
    DataBindingViewHolder<WebInfoEntity>(view) {

    private val mBinding: RvItemConcernBinding =
        DataBindingUtil.bind(view)!!
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
                .setLink(context())
                .setEmotion(context())
                .build()
            launch(Dispatchers.Main) {
//                Log.d(TAG, "content：$content")
                mBinding.concernContentTv.movementMethod = LinkMovementMethod.getInstance()
                mBinding.concernContentTv.text = content
            }
        }
//        mBinding.concernHeadImg.setOnClickListener {
//            if (!view.context.isConnectedNetwork()) {
//                ToastUtil.makeToast("当前网络未连接！")
//            } else {
//                Log.d(TAG, "click")
//                InfoDetailActivity.actionStart(context(), data.userIdStr ?: "")
//            }
//        }
        mBinding.concernContentTv.setOnClickListener {

            if (!view.context.isConnectedNetwork()) {
                ToastUtil.makeToast("当前网络未连接！")
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
        setImg(data)
        initRV(data)

    }

    private fun setImg(data: WebInfoEntity) {
        if (data.picNum ?: 0 == 1) {
            mBinding.concernSingleImgFl.visibility = View.VISIBLE
            val imgWrapBean = ImgWrapBean(data.bmiddlePicUrls!![0], data.originalPicUrls!![0])
            mBinding.concernShowImg.run {
                visibility = View.VISIBLE
                setImageURI(Uri.parse(data.bmiddlePicUrls[0]), context())
                setOnClickListener {
                    ImgViewerActivity.actionStart(
                        context,
                        imgWrapBean
                    )
                }
            }

        } else {
            mBinding.concernSingleImgFl.visibility = View.GONE
            mBinding.concernShowImg.visibility = View.GONE
        }
    }

    private fun initRV(data: WebInfoEntity) {
        Log.d(TAG, "initRV:$data")
        if (data.picNum == null || data.picNum <= 1 || data.bmiddlePicUrls.isNullOrEmpty()) {
            mBinding.concernShowImgRv.visibility = View.GONE
            return
        }
        val mAdapter = ImgAdapter()
        val imgList = ArrayList<ImgWrapBean>()
        var p = 0
        while (p < data.bmiddlePicUrls.size) {
            val imgWrapBean =
                ImgWrapBean(data.bmiddlePicUrls[p], data.originalPicUrls?.get(p) ?: "")
            imgList.add(imgWrapBean)
            p++
        }
        mAdapter.addDataList(imgList)
        mBinding.concernShowImgRv.run {
            visibility = View.VISIBLE
            adapter = mAdapter
            layoutManager = GridLayoutManager(context(), 3)
        }

    }
}

