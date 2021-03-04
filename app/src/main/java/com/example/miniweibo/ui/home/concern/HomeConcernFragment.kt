package com.example.miniweibo.ui.home.concern

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.paging.map
import com.example.miniweibo.R
import com.example.miniweibo.databinding.FragmentHomeConcernBinding
import com.example.miniweibo.databinding.FragmentMineBinding
import com.example.miniweibo.ui.mine.MineViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

@ExperimentalPagingApi
class HomeConcernFragment : Fragment() {
    private val TAG = "HomeConcernFragment"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val homeConcernViewModel: HomeConcernViewModel by viewModels { viewModelFactory }

    private var binding: FragmentHomeConcernBinding? = null

//    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private var mAdapter: ConcernAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        Log.d(TAG,"Run")
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

    private fun init() {
        require(binding != null) { "binding is null" }
        mAdapter = ConcernAdapter()
        binding!!.concernRv.apply {
            adapter = mAdapter
        }
        homeConcernViewModel.postOfData().observe(viewLifecycleOwner) {
            it.map { entity ->
                Log.d(TAG, entity.toString())
            }

            mAdapter!!.submitData(lifecycle, it)
        }
    }

}