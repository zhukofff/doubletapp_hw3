package com.example.doubletapp_hw2

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class HabitDetails {

    abstract val id : String

    @Parcelize
    data class Main (
        override var id: String,
        val title: String,
        val description: String,
        val type: String,
        val priority: String,
        val count: String,
        val period: String
    ) : HabitDetails(), Parcelable

    @Parcelize
    data class ListHabits(
        val list: List<Main>?
    ) : HabitDetails(), Parcelable {

        override val id: String
            get() = "sad"
    }

}

