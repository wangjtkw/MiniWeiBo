package com.example.miniweibo.binding

import android.net.Uri
import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.facebook.drawee.view.SimpleDraweeView
import javax.inject.Inject

@BindingAdapter(value = ["imageUrl"])
fun bindImage(
    iv: SimpleDraweeView,
    url: String?
) {
    url?.let { iv.setImageURI(Uri.parse(it), iv) }
}
//
//class FragmentBindingAdapters @Inject constructor(val fragment: Fragment) {
//    private val TAG = "FragmentBindingAdapters"
//
//    @BindingAdapter(value = ["imageUrl"])
//    fun bindImage(
//        iv: SimpleDraweeView,
//        url: String?
//    ) {
//        Log.d(TAG, url ?: "null")
//        url?.let { iv.setImageURI(Uri.parse(it), fragment) }
//    }
//}