package com.example.doubletapp_hw2

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.doubletapp_hw2.databinding.DialogAddHabitBinding

class AddHabitDialogFragment : DialogFragment() {

    private lateinit var binding: DialogAddHabitBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater;
        binding = DialogAddHabitBinding.inflate(inflater, null, false)


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
            if (binding.habitDescriptionEdit.text!!.isBlank()) {
                showSystemMessage("Please, write description for habit")
            }
            else {
                val mainFragment = HabitsFragment()
                val habit = Bundle()

                habit.putParcelable("Habit", HabitDetails.Main(
                    binding.habitNameEdit.text.toString(),
                    binding.habitDescriptionEdit.text.toString(),
                    getTypeText(),
                    binding.prioritySpinner.selectedItem.toString(),
                    binding.habitCountEdit.text.toString(),
                    binding.habitPeriodEdit.text.toString()
                ))

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

    private fun getTypeText() : String {
        return if (binding.typeGood.isChecked)
            binding.typeGood.text.toString()
        else binding.typeBad.text.toString()
    }
}

