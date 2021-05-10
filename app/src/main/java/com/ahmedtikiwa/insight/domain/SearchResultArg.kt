package com.ahmedtikiwa.insight.domain

import android.os.Parcel
import android.os.Parcelable

data class SearchResultArg(
    val poster: String?,
    val title: String?,
    val year: String?,
    val imdbID: String?,
    val response: Boolean?,
    val error: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(poster)
        parcel.writeString(title)
        parcel.writeString(year)
        parcel.writeString(imdbID)
        parcel.writeValue(response)
        parcel.writeString(error)
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
