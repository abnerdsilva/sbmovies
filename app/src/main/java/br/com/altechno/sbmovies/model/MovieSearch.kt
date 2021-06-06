package br.com.altechno.sbmovies.model

import android.os.Parcel
import android.os.Parcelable

class MovieSearch(
    val Title: String?,
    val Year: String?,
    val imdbID: String?,
    val Type: String?,
    val Poster: String?,
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
        parcel.writeString(Title)
        parcel.writeString(Year)
        parcel.writeString(imdbID)
        parcel.writeString(Type)
        parcel.writeString(Poster)
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
