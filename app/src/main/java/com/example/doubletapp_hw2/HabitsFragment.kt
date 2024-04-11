package com.example.doubletapp_hw2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doubletapp_hw2.AddHabitDialogFragment.Companion.ARGS_ACTION
import com.example.doubletapp_hw2.databinding.FragmentListHabitBinding

class HabitsFragment : Fragment() {


    private lateinit var binding : FragmentListHabitBinding

    private val habitAdapter by lazy(LazyThreadSafetyMode.NONE) {
        HabitAdapter(::onEditClick)
    }

    private lateinit var viewModel: HabitViewModel
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("habit", "On create call, init VM")
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T: ViewModel> create(modelClass: Class<T>) : T {
                return HabitViewModel() as T
            }
        }).get(HabitViewModel::class.java)

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
                viewModel.deleteHabit(id)
            }
            Log.v(REQUEST_KEYS.Habit.name, "onCreateView $result $bundle $type $action")
            if (result != null && result.type.lowercase() == type.lowercase()) {
                if (action == ACTIONS.EDIT.name) {
                    viewModel.editHabit(result)
                } else {
                    viewModel.addHabit(result)
                    Log.v(REQUEST_KEYS.Habit.name, "Add habit inside vm call")
                }
            }
        }
        binding = FragmentListHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*if (savedInstanceState != null) {
            habitList =
                savedInstanceState.getParcelable<HabitDetails.ListHabits>(REQUEST_KEYS.Habit.toString())?.list?.toMutableList()
            Log.v(REQUEST_KEYS.Habit.name, "Get type $type")
            habitList?.map {
                it.type.lowercase() == type.lowercase()
            }
        }*/
        viewModel.habits.observe(viewLifecycleOwner, ::handleUI)
        Log.v(REQUEST_KEYS.Habit.name, "onViewCreated")
        binding.listHabits.adapter = habitAdapter

    }

    private fun handleUI(habits: MutableList<HabitDetails.Main>) {
        Log.v(REQUEST_KEYS.Habit.name, "Get habits = $habits")
        habitAdapter.submitList(habits as MutableList<HabitDetails>)
    }
    private fun onEditClick(item: HabitDetails.Main) {
        val s = AddHabitDialogFragment.newInstance(ACTIONS.EDIT.name, type,  item)
        s.show(parentFragmentManager, ACTIONS.EDIT.name)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.v(REQUEST_KEYS.Habit.name, "on save instance state in fragment call")
        outState.run {
            putParcelable(REQUEST_KEYS.Habit.toString(), HabitDetails.ListHabits(list = viewModel.habits.value))
        }
        outState.run {
            putString(ARGS_TYPE, type)
        }
        super.onSaveInstanceState(outState)
    }


}