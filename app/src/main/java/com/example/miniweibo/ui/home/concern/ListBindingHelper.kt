package com.example.miniweibo.ui.home.concern

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

abstract class ListBindingHelper<A : RecyclerView.Adapter<VH>, VH : RecyclerView.ViewHolder, VM : ViewModel>(
    protected val viewModel: VM
) {
    protected var adapter: A? = null

    abstract fun init()

    abstract fun initData()


}