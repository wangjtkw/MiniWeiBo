package com.example.miniweibo.ui.home.concern

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import com.example.miniweibo.R
import com.example.miniweibo.data.bean.entity.WebInfoEntity
import com.example.miniweibo.databinding.RvItemConcernBinding

class ConcernAdapter :
    PagingDataAdapter<WebInfoEntity, ConcernViewHolder>(WebInfoEntity.diffCallback) {
    override fun onBindViewHolder(holder: ConcernViewHolder, position: Int) {
        val data = getItem(position)
        data?.let {
            holder.bindData(it, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcernViewHolder {
        val binding = DataBindingUtil.inflate<RvItemConcernBinding>(
            LayoutInflater.from(parent.context),
            R.layout.rv_item_concern,
            parent,
            false
        )
        return ConcernViewHolder(binding)
    }

//    private fun inflateView(viewGroup: ViewGroup, @LayoutRes viewType: Int): View {
//    }
}