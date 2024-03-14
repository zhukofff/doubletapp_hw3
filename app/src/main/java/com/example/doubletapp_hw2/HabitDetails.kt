package com.example.doubletapp_hw2

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class HabitDetails {

    abstract val title : String

    @Parcelize
    data class Main (
        override val title: String,
        val description: String,
        val type: String,
        val priority: String,
        val count: String,
        val period: String
    ) : HabitDetails(), Parcelable {
        override fun describeContents(): Int {
            return 0
        }



    }

    @Parcelize
    data class ListHabits(
        val list: List<Main>?
    ) : HabitDetails(), Parcelable {
        override fun describeContents(): Int {
            return 0
        }


        override val title: String
            get() = "sad"
    }

}

