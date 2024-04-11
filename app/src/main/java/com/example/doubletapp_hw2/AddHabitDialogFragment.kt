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

    val type by lazy { arguments?.getString(ARGS_TYPE) ?: "default"}

    private lateinit var viewModel: CreateEditHabitViewModel

    companion object {
        const val ARGS_ACTION = "args_action"
        private const val ARGS_HABIT = "args_habit"
        private const val ARGS_NUM = "args_num"
        private const val ARGS_TYPE = "args_type"

        fun newInstance(action: String, type: String, item: HabitDetails.Main? = null) : AddHabitDialogFragment {
            val fragment = AddHabitDialogFragment()
            val bundle = Bundle()
            bundle.putString(ARGS_ACTION, action)
            //bundle.putString(ARGS_NUM, num)
            bundle.putString(ARGS_TYPE, type)
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
                showSystemMessage(getString(R.string.you_need_to_type_the_name_of_habit))
            }
            else if (binding.habitDescriptionEdit.text!!.isBlank()) {
                showSystemMessage(getString(R.string.please_write_description_for_habit))
            }
            else if (binding.habitPeriodEdit.text!!.isBlank()) {
                showSystemMessage(getString(R.string.please_write_period_for_habit))
            }
            else if (binding.habitCountEdit.text!!.isBlank()) {
                showSystemMessage(getString(R.string.please_write_count_for_habit))
            }
            else {

                val habit = Bundle()
                habit.putParcelable(getTypeText(), HabitDetails.Main(
                    if (action == ACTIONS.EDIT.name) item.id else "0" ,
                    binding.habitNameEdit.text.toString(),
                    binding.habitDescriptionEdit.text.toString(),
                    getTypeText(),
                    binding.prioritySpinner.selectedItem.toString(),
                    binding.habitCountEdit.text.toString(),
                    binding.habitPeriodEdit.text.toString()
                ))

                if (action == ACTIONS.EDIT.name && getTypeText() != item.type)
                    habit.putString(ARGS_ACTION, ACTIONS.ADD.name)
                else {
                    habit.putString(ARGS_ACTION, action)
                }
                Log.v("List of habits", "Type = ${getTypeText()}, $habit")
                requireActivity().supportFragmentManager.setFragmentResult(
                    getTypeText(),
                    habit)
                if (action == ACTIONS.EDIT.name) {
                    if (item.type != getTypeText()) {
                        val habit2 = Bundle()
                        habit2.putString(REQUEST_KEYS.DELETE.name, item.id)
                        requireActivity().supportFragmentManager.setFragmentResult(
                            item.type,
                            habit2
                        )
                    }
                }
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
        if (item.type.lowercase() == Type.GOOD.name.lowercase())
            binding.typeGood.isChecked = true
        else
            binding.typeBad.isChecked = true
    }

    private fun setFragmentToEdit() {
        binding.addHabit.text = "Edit habit"
        binding.title.text = "Edit habit"
    }

    private fun getTypeText() : String {
        return if (binding.typeGood.isChecked) {
            Type.GOOD.name
        }
        else { Type.BAD.name }
    }
}

