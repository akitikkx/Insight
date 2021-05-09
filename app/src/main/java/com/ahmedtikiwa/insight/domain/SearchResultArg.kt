package com.ahmedtikiwa.insight.domain

import android.os.Parcel
import android.os.Parcelable

data class SearchResultArg(
    val poster: String?,
    val title: String?,
    val year: String?,
    val imdbID: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(poster)
        parcel.writeString(title)
        parcel.writeString(year)
        parcel.writeString(imdbID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchResultArg> {
        override fun createFromParcel(parcel: Parcel): SearchResultArg {
            return SearchResultArg(parcel)
        }

        override fun newArray(size: Int): Array<SearchResultArg?> {
            return arrayOfNulls(size)
        }
    }
}
