package com.example.miniweibo.ui.mine

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
import com.example.miniweibo.R
import com.example.miniweibo.databinding.FragmentMineBinding
import com.facebook.drawee.view.SimpleDraweeView
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MineFragment : Fragment() {
    private val TAG = "MineFragment"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var binding: FragmentMineBinding? = null

//    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val userViewModel: MineViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_mine,
            container,
            false,
//            dataBindingComponent
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.user.observe(viewLifecycleOwner) {
            binding!!.userInfoEntity = it.data
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MineFragment()
    }
}