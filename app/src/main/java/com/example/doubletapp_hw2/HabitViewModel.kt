package com.example.doubletapp_hw2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HabitViewModel : ViewModel() {

    private val _habits = MutableLiveData<MutableList<HabitDetails.Main>>()
    val habits: LiveData<MutableList<HabitDetails.Main>> = _habits

    fun addHabit(item: HabitDetails.Main) {
        item.id = _habits.value?.size.toString()
        _habits.value?.add(item)
    }


    fun deleteHabit(id: String) {
        _habits.value?.removeIf { it.id == id }
    }

    fun editHabit(result: HabitDetails.Main) {
        _habits.value?.map { if (it.id == result.id) result else it }
    }

}