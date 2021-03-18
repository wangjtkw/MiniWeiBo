package com.example.miniweibo.ui.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniweibo.data.bean.Resource
import com.example.miniweibo.data.bean.entity.ImgEntity
import com.example.miniweibo.data.datasource.ImgDataSource
import javax.inject.Inject

class VideoViewModel @Inject constructor(private val imgDataSource: ImgDataSource) : ViewModel() {
//    private val l:MutableLiveData

    fun getImg(): LiveData<Resource<List<ImgEntity>>> = imgDataSource.getImg(viewModelScope)
}