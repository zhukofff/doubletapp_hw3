package com.example.doubletapp_hw2

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener

import com.example.doubletapp_hw2.databinding.FragmentListHabitBinding

class HabitsFragment : Fragment() {

/*
    private val binding: FragmentListHabitBinding by viewBinding(FragmentListHabitBinding::bind)
*/
    private lateinit var binding : FragmentListHabitBinding

    private val habitAdapter by lazy(LazyThreadSafetyMode.NONE) {
        HabitAdapter()
    }

    private var habitList : MutableList<HabitDetails.Main>? = mutableListOf<HabitDetails.Main>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setFragmentResultListener("Habit") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported.
            val result = bundle.getParcelable<HabitDetails.Main>("Habit")
            // Do something with the result.

            Log.v("List of habits", "onCreateView $result")
            if (result != null)
                habitList?.add(result)
            habitAdapter.notifyItemInserted(habitList?.size?.minus(1) ?: 0)
        }
        binding = FragmentListHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null)
            habitList = savedInstanceState.getParcelable<HabitDetails.ListHabits>("Habit")?.list?.toMutableList()

        Log.v("List of habits", "onViewCreated $habitList")
        habitAdapter.submitList(habitList as List<HabitDetails.Main>)
        binding.listHabits.adapter = habitAdapter


        binding.addHabit.setOnClickListener {
            val s = AddHabitDialogFragment()
            s.show(parentFragmentManager, "Add habit")
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {

        outState.run {
            putParcelable("HabitList", HabitDetails.ListHabits(list = habitList))
        }
        super.onSaveInstanceState(outState)
    }

    /*override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        savedInstanceState.run {

        }
    }
*/
}