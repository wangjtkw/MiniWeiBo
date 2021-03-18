package com.example.miniweibo.data.bean.bean

import android.os.Parcel
import android.os.Parcelable

data class WebViewJumpBean(
    val url: String?,
    val uid: String?,
    val webId: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeString(uid)
        parcel.writeString(webId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WebViewJumpBean> {

        override fun createFromParcel(parcel: Parcel): WebViewJumpBean {
            return WebViewJumpBean(parcel)
        }

        override fun newArray(size: Int): Array<WebViewJumpBean?> {
            return arrayOfNulls(size)
        }
    }
}