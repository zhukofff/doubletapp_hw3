package com.example.doubletapp_hw2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.example.doubletapp_hw2.AddHabitDialogFragment.Companion.ARGS_ACTION
import com.example.doubletapp_hw2.databinding.FragmentListHabitBinding

class HabitsFragment : Fragment() {


    private lateinit var binding : FragmentListHabitBinding

    private val habitAdapter by lazy(LazyThreadSafetyMode.NONE) {
        HabitAdapter(::onEditClick)
    }

    val type by lazy { arguments?.getString(ARGS_TYPE) ?: "default"}

    companion object {
        private const val ARGS_TYPE = "args_type"
        private const val ARGS_HABIT = "args_habit"

        fun newInstance(type: String) : HabitsFragment {
            val fragment = HabitsFragment()
            val bundle = Bundle()
            bundle.putString(ARGS_TYPE, type)
            fragment.arguments = bundle

            return fragment
        }
    }

    var habitList : MutableList<HabitDetails.Main>? = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("habit", "On create call")


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().supportFragmentManager.setFragmentResultListener(type, this) { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported.
            val result = bundle.getParcelable<HabitDetails.Main>(type)
            // Do something with the result.
            val action = bundle.getString(ARGS_ACTION)
            val id = bundle.getString(REQUEST_KEYS.DELETE.name)
            if (id != null) {
                Log.v(REQUEST_KEYS.Habit.name, "DELETE FROM LIST WHERE $id")
                habitList?.removeIf { it.id == id }
                habitAdapter.submitList(habitList as MutableList<HabitDetails>)
            }
            Log.v(REQUEST_KEYS.Habit.name, "onCreateView $result $bundle $type $action")
            if (result != null && result.type.lowercase() == type.lowercase()) {
                if (action == ACTIONS.EDIT.name) {
                   habitAdapter.submitList(habitList?.map { if (it.id == result.id) result else it } as MutableList<HabitDetails>)
                } else {
                    result.id = habitList?.size.toString()
                    habitList?.add(result)
                    Log.v(REQUEST_KEYS.Habit.name, "$habitList")
                    habitAdapter.submitList(habitList as MutableList<HabitDetails>)
                }
            }
        }
        binding = FragmentListHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            habitList =
                savedInstanceState.getParcelable<HabitDetails.ListHabits>(REQUEST_KEYS.Habit.toString())?.list?.toMutableList()
            Log.v(REQUEST_KEYS.Habit.name, "Get type $type")
            habitList?.map {
                it.type.lowercase() == type.lowercase()
            }
        }
        Log.v(REQUEST_KEYS.Habit.name, "onViewCreated $habitList")

        habitAdapter.submitList(habitList as MutableList<HabitDetails>)
        binding.listHabits.adapter = habitAdapter

    }

    private fun onEditClick(item: HabitDetails.Main) {
        val s = AddHabitDialogFragment.newInstance(ACTIONS.EDIT.name, habitList?.size.toString() ?: "0", type,  item)
        s.show(parentFragmentManager, ACTIONS.EDIT.name)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.v(REQUEST_KEYS.Habit.name, "on save instance state in fragment call")
        outState.run {
            putParcelable(REQUEST_KEYS.Habit.toString(), HabitDetails.ListHabits(list = habitList))
        }
        outState.run {
            putString(ARGS_TYPE, type)
        }
        super.onSaveInstanceState(outState)
    }

    private fun makeFragmentName(viewId: Int, id: Long): String {
        return "android:switcher:$viewId:$id"
    }

}