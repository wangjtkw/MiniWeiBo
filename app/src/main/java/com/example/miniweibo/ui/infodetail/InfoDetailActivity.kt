package com.example.miniweibo.ui.infodetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miniweibo.R
import com.example.miniweibo.data.bean.Status
import com.example.miniweibo.databinding.ActivityInfoDetailBinding
import com.example.miniweibo.ui.home.concern.ConcernAdapter
import com.example.miniweibo.util.ToastUtil
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@ExperimentalPagingApi
class InfoDetailActivity : AppCompatActivity(), HasAndroidInjector {

    private val TAG = "InfoDetailActivity"

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    private var uid: String = ""

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val infoDetailViewModel: InfoDetailViewModel by viewModels { viewModelFactory }

    private var mBinding: ActivityInfoDetailBinding? = null

    private var mAdapter: ConcernAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_info_detail)
        uid = intent.getStringExtra(INFO_DETAIL_UID) ?: ""
        checkUid()
        init()
    }

    private fun init() {
        initUserInfo()
        initRv()
        initSwipeToRefresh()
    }

    private fun initUserInfo() {
        infoDetailViewModel.getUserInfo(uid).observe(this) {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    mBinding!!.userInfoEntity = it.data
                }
                Status.ERROR -> {

                }
            }
        }

    }

    private fun initRv() {
        mAdapter = ConcernAdapter()
        mBinding!!.infoDetailRv.adapter = mAdapter
        mBinding!!.infoDetailRv.layoutManager = LinearLayoutManager(this)
        infoDetailViewModel.getWebInfo(uid).observe(this) {
            mAdapter!!.submitData(lifecycle, it)
        }

        lifecycleScope.launchWhenCreated {
            mAdapter!!.loadStateFlow.collectLatest { loadStates ->
                mBinding!!.infoDetailSwipeRefresh.isRefreshing =
                    (loadStates.refresh is LoadState.Loading)
            }
        }
    }

    private fun initSwipeToRefresh() {
        mBinding!!.infoDetailSwipeRefresh.setOnRefreshListener { mAdapter!!.refresh() }
    }

    private fun checkUid() {
        if (uid.isEmpty()) {
            ToastUtil.makeToast("uid is null")
            finish()
        }
    }


    companion object {
        const val INFO_DETAIL_UID = "info_detail_uid"

        fun actionStart(context: Context, uid: String) {
            val intent = Intent(context, InfoDetailActivity::class.java)
            intent.putExtra(INFO_DETAIL_UID, uid)
            context.startActivity(intent)
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}