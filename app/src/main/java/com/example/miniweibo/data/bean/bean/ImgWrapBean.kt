package com.example.miniweibo.data.bean.bean

import android.os.Parcel
import android.os.Parcelable

data class ImgWrapBean(val url: String?, var originUrl: String? = "") : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeString(originUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImgWrapBean> {
        override fun createFromParcel(parcel: Parcel): ImgWrapBean {
            return ImgWrapBean(parcel)
        }

        override fun newArray(size: Int): Array<ImgWrapBean?> {
            return arrayOfNulls(size)
        }
    }
}