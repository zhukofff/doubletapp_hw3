package com.example.doubletapp_hw2

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntDef
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doubletapp_hw2.databinding.HabitItemBinding

sealed class HabitItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

class HabitAdapter()
    : ListAdapter<HabitDetails, HabitItemViewHolder>(HabitCallback) {

    @HabitDetailsType
    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is HabitDetails.Main -> VIEW_TYPE_MAIN
            is HabitDetails.ListHabits -> 2
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        @HabitDetailsType viewType: Int): HabitItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_MAIN -> {
                Log.v("Adapter", "Successflly create view holder")
                val binding = HabitItemBinding.inflate(layoutInflater)
                MainHabitViewHolder(binding)
            }
            else -> {
                throw IllegalArgumentException("Invalid viewType = $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: HabitItemViewHolder, position: Int) {
        val item = getItem(position)
        when {
            ((item is HabitDetails.Main) && (holder is MainHabitViewHolder)) -> {
                Log.v("Adapter", "Successfully bind view holder")
                holder.bind(item)
            }

        }
    }

    object HabitCallback: DiffUtil.ItemCallback<HabitDetails>() {
        override fun areContentsTheSame(oldItem: HabitDetails, newItem: HabitDetails): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: HabitDetails, newItem: HabitDetails): Boolean {
            return oldItem.title == newItem.title
        }
    }
}

@Retention(AnnotationRetention.SOURCE)
@IntDef(
    flag = true,
    value = [VIEW_TYPE_MAIN]
)

private annotation class HabitDetailsType

private const val VIEW_TYPE_MAIN = 1
