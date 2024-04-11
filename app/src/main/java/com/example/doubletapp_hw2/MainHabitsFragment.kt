package com.example.doubletapp_hw2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.doubletapp_hw2.databinding.FragmentMainHabitsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator


class MainHabitsFragment : Fragment() {

    private val binding: FragmentMainHabitsBinding by viewBinding()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_habits, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentArray = arrayOf(
            requireActivity().getString(R.string.Good_habits),
            requireActivity().getString(R.string.Bad_habits)
        )

        val adapter = HabitsViewPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = fragmentArray[position]
        }.attach()
        /*requireActivity().supportFragmentManager
            .setFragmentResultListener(Type.GOOD.name, viewLifecycleOwner) { requestKey, bundle ->
                Log.v("Habit", "Get results $bundle")
            }*/
        val bottomSheetBehavior: BottomSheetBehavior<*> =
            BottomSheetBehavior.from<View>(binding.bottomSheet)

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        binding.addHabit.setOnClickListener {
            val myFragment = childFragmentManager.findFragmentByTag("f" + binding.viewPager.currentItem) as HabitsFragment
            val s = AddHabitDialogFragment.newInstance(ACTIONS.ADD.name, myFragment.type)
            s.show(parentFragmentManager, ACTIONS.ADD.name)
        }
    }

}