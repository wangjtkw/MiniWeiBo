package com.example.miniweibo.ui.home.concern

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.miniweibo.R

class ImgAdapter : RecyclerView.Adapter<ImgViewHolder>() {
    private val dataList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
        val view = inflateView(parent, R.layout.rv_item_concern_img)
        return ImgViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImgViewHolder, position: Int) {
        val data = getItem(position)
        data.let {
            holder.bindData(it, position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun addDataList(list: List<String>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): String {
        return dataList[position]
    }

    private fun inflateView(viewGroup: ViewGroup, @LayoutRes viewType: Int): View {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return layoutInflater.inflate(viewType, viewGroup, false)
    }
}