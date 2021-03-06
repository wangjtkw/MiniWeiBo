package com.example.miniweibo.ui.home.concern

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.miniweibo.R
import com.example.miniweibo.data.bean.entity.RemoteKeyEntity
import com.example.miniweibo.databinding.FragmentHomeConcernBinding
import com.example.miniweibo.ext.isConnectedNetwork
import com.example.miniweibo.sdk.SDKUtil
import com.example.miniweibo.util.ToastUtil
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@ExperimentalPagingApi
class HomeConcernFragment : Fragment() {
    private val TAG = "HomeConcernFragment"

    private var paramType: String = ""

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val homeViewModel: HomeViewModel by viewModels { viewModelFactory }

    private var binding: FragmentHomeConcernBinding? = null

    private var mAdapter: ConcernAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        paramType = arguments?.getString(PARAM_TYPE) ?: ""
        Log.d(TAG, "argument:$arguments")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home_concern,
            container,
            false,
//            dataBindingComponent
        )
        init()
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        if (!requireContext().isConnectedNetwork()) {
            ToastUtil.makeToast("当前网络未连接！")
        }
    }

    private fun init() {
        require(binding != null) { "binding is null" }
        initRV()
        initSwipeToRefresh()
    }

    private fun initRV() {
        mAdapter = ConcernAdapter()
        binding!!.concernRv.adapter = mAdapter
        binding!!.concernRv.layoutManager = LinearLayoutManager(requireContext())
        when (paramType) {
            RemoteKeyEntity.TYPE_CONCERN -> {
                homeViewModel
                    .postOfConcernData()
                    .observe(viewLifecycleOwner) {
                        mAdapter!!.submitData(lifecycle, it)
                    }
            }
            RemoteKeyEntity.TYPE_OTHER -> {
                homeViewModel
                    .postOfMineData(SDKUtil.getSDKUtil().getAccessTokenBean().uid)
                    .observe(viewLifecycleOwner) {
                        mAdapter!!.submitData(lifecycle, it)
                    }
            }
        }



        lifecycleScope.launchWhenCreated {
            mAdapter!!.loadStateFlow.collectLatest { loadStates ->
                binding!!.concernSwipeRefresh.isRefreshing =
                    (loadStates.refresh is LoadState.Loading)
            }
        }
    }

    private fun initSwipeToRefresh() {
        binding!!.concernSwipeRefresh.setOnRefreshListener { mAdapter!!.refresh() }
    }

    companion object {

        private const val PARAM_TYPE = "PARAM_TYPE"

        @JvmStatic
        fun newInstance(paramType: String) =
            HomeConcernFragment().apply {
                arguments = Bundle().apply {
                    putString(PARAM_TYPE, paramType)
                }
            }
    }
}