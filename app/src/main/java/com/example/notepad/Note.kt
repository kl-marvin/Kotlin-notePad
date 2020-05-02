package com.example.notepad

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable


// note implémente l'Interface Parcelable (transfère de Note sur != activity)
// et Serealizable (java) pour transformer l'object en fichier et inverssement
data class Note(var title: String? = "", var text: String? = "", var fileName: String? = ""): Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(text)
        parcel.writeString(fileName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        // id unique pour reconnaitre les versions de Note
        private val serialVersionUIid: Long = 424242424242
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }


}