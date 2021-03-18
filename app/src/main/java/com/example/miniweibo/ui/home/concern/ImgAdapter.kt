package com.example.miniweibo.ui.home.concern

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.miniweibo.R
import com.example.miniweibo.data.bean.bean.ImgWrapBean

class ImgAdapter : RecyclerView.Adapter<ImgViewHolder>() {
    private val dataList = ArrayList<ImgWrapBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
        val view = inflateView(parent, R.layout.rv_item_concern_img)
        return ImgViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImgViewHolder, position: Int) {
        holder.bindData(dataList, position)
    }

    override fun getItemCount(): Int {
        return if (dataList.size > 9) {
            9
        } else {
            dataList.size
        }
    }

    fun addDataList(list: List<ImgWrapBean>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }


    private fun inflateView(viewGroup: ViewGroup, @LayoutRes viewType: Int): View {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return layoutInflater.inflate(viewType, viewGroup, false)
    }
}