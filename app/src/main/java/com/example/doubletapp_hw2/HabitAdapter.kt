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

class HabitAdapter(
    private val onEditClick: (HabitDetails.Main) -> Unit
)
    : ListAdapter<HabitDetails, HabitItemViewHolder>(HabitCallback) {

    var items : MutableList<HabitDetails> = mutableListOf()
        private set

    @HabitDetailsType
    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is HabitDetails.Main -> VIEW_TYPE_MAIN
            is HabitDetails.ListHabits -> 2
        }
    }

    override fun submitList(list: MutableList<HabitDetails>?) {
        items = list.orEmpty().toMutableList()
        super.submitList(items)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        @HabitDetailsType viewType: Int): HabitItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_MAIN -> {
                Log.v("Adapter", "Successflly create view holder")
                MainHabitViewHolder.from(parent, onEditClick)
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
                Log.v("habit", "Successfully bind view holder")
                holder.bind(item)
            }

        }
    }

    override fun onBindViewHolder(
        holder: HabitItemViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            Log.v("habit", "Payload is empty!")
            super.onBindViewHolder(holder, position, payloads)
        } else {
            Log.v("habit", "Payload is not empty! $payloads")

            val combinedChange = createCombinedPayload(payloads as List<Change<*>>)
            val oldData = combinedChange.oldData
            val newData = combinedChange.newData

            if (oldData is HabitDetails.Main && newData is HabitDetails.Main && holder is MainHabitViewHolder) {
                Log.v("habit", "start to change newData is $newData")
                holder.binding.habitCounter.text = newData.count
                holder.binding.habitTitle.text = newData.title
                holder.binding.habitCounter.text = newData.count
                holder.binding.habitDescription.text = newData.description
                holder.binding.habitPeriod.text = newData.period
                holder.binding.habitPriority.text = newData.priority
                if (newData.type == "Good")
                    holder.binding.habitType.setImageResource(R.drawable.circle_green_24)
                else
                    holder.binding.habitType.setImageResource(R.drawable.circle_red_24)
            }

            }
    }

    object HabitCallback: DiffUtil.ItemCallback<HabitDetails>() {
        override fun areContentsTheSame(oldItem: HabitDetails, newItem: HabitDetails): Boolean {
            Log.v("habit", "are contents the same call $newItem $oldItem")
            return if (oldItem is HabitDetails.Main && newItem is HabitDetails.Main) {
                !(oldItem.description != newItem.description || oldItem.type != newItem.type || oldItem.title != newItem.title || oldItem.period != newItem.period || oldItem.count != newItem.count || oldItem.priority != newItem.priority)
            }
            else oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: HabitDetails, newItem: HabitDetails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun getChangePayload(oldItem: HabitDetails, newItem: HabitDetails): Any? {
            return Change(oldItem, newItem)
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
