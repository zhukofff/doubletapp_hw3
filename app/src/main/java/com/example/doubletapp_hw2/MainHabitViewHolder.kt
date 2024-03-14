package com.example.doubletapp_hw2

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.doubletapp_hw2.databinding.HabitItemBinding

class MainHabitViewHolder(
    private val binding: HabitItemBinding
) : HabitItemViewHolder(binding.root) {

    fun bind(item: HabitDetails.Main) {
        binding.habitTitle.text = item.title
        binding.habitCounter.text = item.count
        binding.habitDescription.text = item.description
        binding.habitPeriod.text = item.period
        binding.habitPriority.text = item.priority
        if (item.type == "Good")
            binding.habitType.setImageResource(R.drawable::class.java.getId("circle_green_24"))
        else
            binding.habitType.setImageResource(R.drawable::class.java.getId("circle_red_24"))

    }

    companion object {
        fun from(parent: ViewGroup): MainHabitViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = HabitItemBinding.inflate(layoutInflater, parent, false)
            return MainHabitViewHolder(binding)
        }
    }
}
