package com.example.doubletapp_hw2

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.doubletapp_hw2.databinding.DialogAddHabitBinding

class AddHabitDialogFragment : DialogFragment() {

    companion object {
        const val ARGS_ACTION = "args_action"
        private const val ARGS_HABIT = "args_habit"
        private const val ARGS_NUM = "args_num"

        fun newInstance(action: String, num: String, item: HabitDetails.Main? = null) : AddHabitDialogFragment {
            val fragment = AddHabitDialogFragment()
            val bundle = Bundle()
            bundle.putString(ARGS_ACTION, action)
            bundle.putString(ARGS_NUM, num)
            if (item != null)
                bundle.putParcelable(ARGS_HABIT, item)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val action: String? by lazy { arguments?.getString(ARGS_ACTION) ?: "default"}
    private val num: String? by lazy { arguments?.getString(ARGS_NUM)}
    private lateinit var item: HabitDetails.Main
    private lateinit var binding: DialogAddHabitBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater;
        binding = DialogAddHabitBinding.inflate(inflater, null, false)
        arguments.let {
            if (action == ACTIONS.EDIT.name)
                setFragmentToEdit()
            if (it != null  && action == ACTIONS.EDIT.name) {
                item = it.getParcelable(ARGS_HABIT)!!
                setHabitDetails(item)

            }
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            binding.prioritySpinner.adapter = adapter
        }

        binding.addHabit.setOnClickListener {
            if (binding.habitNameEdit.text!!.isBlank()) {
                showSystemMessage("You need to type the name of habit")
            }
            else if (binding.habitDescriptionEdit.text!!.isBlank()) {
                showSystemMessage("Please, write description for habit")
            }
            else if (binding.habitPeriodEdit.text!!.isBlank()) {
                showSystemMessage("Please, write period for habit")
            }
            else if (binding.habitCountEdit.text!!.isBlank()) {
                showSystemMessage("Please, write count for habit")
            }
            else {
                val mainFragment = HabitsFragment()
                val habit = Bundle()

                habit.putParcelable("Habit", HabitDetails.Main(
                    if (action == ACTIONS.EDIT.name) item.id else num.toString() ,
                    binding.habitNameEdit.text.toString(),
                    binding.habitDescriptionEdit.text.toString(),
                    getTypeText(),
                    binding.prioritySpinner.selectedItem.toString(),
                    binding.habitCountEdit.text.toString(),
                    binding.habitPeriodEdit.text.toString()
                ))
                habit.putString(ARGS_ACTION, action)
                Log.v("List of habits", "$habit")
                mainFragment.arguments = habit
                setFragmentResult(
                    "Habit",
                    habit
                )
                dialog?.dismiss()

            }
        }
        binding.back.setOnClickListener {
            dialog?.dismiss()
        }
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setView(binding.root)

            builder.create().apply {
                window?.setBackgroundDrawableResource(android.R.color.transparent)
            }

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun setHabitDetails(item: HabitDetails.Main) {
        binding.habitCountEdit.text = Editable.Factory.getInstance().newEditable(item.count)
        binding.habitDescriptionEdit.text = Editable.Factory.getInstance().newEditable(item.description)
        binding.habitNameEdit.text = Editable.Factory.getInstance().newEditable(item.title)
        binding.habitPeriodEdit.text = Editable.Factory.getInstance().newEditable(item.period)
        if (item.type == "Good")
            binding.typeGood.isChecked = true
        else
            binding.typeBad.isChecked = true
    }

    private fun setFragmentToEdit() {
        binding.addHabit.text = "Edit habit"
        binding.title.text = "Edit habit"
    }

    private fun getTypeText() : String {
        return if (binding.typeGood.isChecked)
            binding.typeGood.text.toString()
        else binding.typeBad.text.toString()
    }
}

