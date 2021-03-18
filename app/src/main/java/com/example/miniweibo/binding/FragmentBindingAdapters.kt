package com.example.miniweibo.binding

import android.net.Uri
import androidx.databinding.BindingAdapter
import com.facebook.drawee.view.SimpleDraweeView

@BindingAdapter(value = ["imageUrl"])
fun bindImage(
    iv: SimpleDraweeView,
    url: String?
) {
    url?.let { iv.setImageURI(Uri.parse(it), iv) }
}