package com.example.miniweibo.ui.home.concern

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingDataAdapter
import com.example.miniweibo.R
import com.example.miniweibo.data.bean.entity.WebInfoEntity

@ExperimentalPagingApi
class ConcernAdapter :
    PagingDataAdapter<WebInfoEntity, ConcernViewHolder>(WebInfoEntity.diffCallback) {
    override fun onBindViewHolder(holder: ConcernViewHolder, position: Int) {
        val data = getItem(position)
        data?.let {
            holder.bindData(it, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcernViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_concern
        ,parent,false)
        return ConcernViewHolder(view)
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConcernViewHolder {
//        val view =
//            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_concern, parent, false)
//
////        val binding = DataBindingUtil.inflate<RvItemConcernBinding>(
////            LayoutInflater.from(parent.context),
////            R.layout.rv_item_concern,
////            parent,
////            false
////        )
//        return ConcernViewHolder(view)
//    }

//    private fun inflateView(viewGroup: ViewGroup, @LayoutRes viewType: Int): View {
//    }
}