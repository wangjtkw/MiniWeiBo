package com.example.miniweibo.ui.home.concern

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.PagingDataAdapter
import com.example.miniweibo.R
import com.example.miniweibo.data.bean.entity.WebInfoEntity

class ConcernAdapter :
    PagingDataAdapter<WebInfoEntity, ConcernViewHolder>(WebInfoEntity.diffCallback) {
    override fun onBindViewHolder(holder: ConcernViewHolder, position: Int) {
        val data = getItem(position)
        data?.let {
            holder.bindData(it, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcernViewHolder {
        val view = inflateView(parent, R.layout.rv_item_concern)

        return ConcernViewHolder(view)
    }

    private fun inflateView(viewGroup: ViewGroup, @LayoutRes viewType: Int): View {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return layoutInflater.inflate(viewType, viewGroup, false)
    }
}