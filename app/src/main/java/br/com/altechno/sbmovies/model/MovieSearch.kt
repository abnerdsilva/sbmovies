package br.com.altechno.sbmovies.model

import android.os.Parcel
import android.os.Parcelable

class MovieSearch(
    val title: String?,
    val year: String?,
    val imdbID: String?,
    val type: String?,
    val poster: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(year)
        parcel.writeString(imdbID)
        parcel.writeString(type)
        parcel.writeString(poster)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieSearch> {
        override fun createFromParcel(parcel: Parcel): MovieSearch {
            return MovieSearch(parcel)
        }

        override fun newArray(size: Int): Array<MovieSearch?> {
            return arrayOfNulls(size)
        }
    }

}
