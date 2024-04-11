package com.example.doubletapp_hw2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat.getDrawable
import com.example.doubletapp_hw2.databinding.HabitItemBinding

class MainHabitViewHolder(
    val binding: HabitItemBinding,
    private val onEditClick: (HabitDetails.Main) -> Unit
) : HabitItemViewHolder(binding.root) {

    var type = ""
    fun bind(item: HabitDetails.Main) {
        binding.habitTitle.text = item.title
        binding.habitCounter.text = item.count
        binding.habitDescription.text = item.description
        binding.habitPeriod.text = item.period
        binding.habitPriority.text = item.priority
        if (item.type.lowercase() == Type.GOOD.name.lowercase()) {
            type = Type.GOOD.name
            binding.habitType.setImageResource(R.drawable.circle_green_24)
        }
        else {
            type = Type.BAD.name
            binding.habitType.setImageResource(R.drawable.circle_red_24)
        }

        binding.habitEdit.setOnClickListener {
            onEditClick(HabitDetails.Main(
                item.id,
                binding.habitTitle.text.toString(),
                binding.habitDescription.text.toString(),
                type,
                binding.habitPriority.text.toString(),
                binding.habitCounter.text.toString(),
                binding.habitPeriod.text.toString()
            ))
        }
    }

    companion object {
        fun from(parent: ViewGroup, onEditClick: (HabitDetails.Main) -> Unit): MainHabitViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = HabitItemBinding.inflate(layoutInflater, parent, false)
            return MainHabitViewHolder(binding, onEditClick)
        }
    }
}
