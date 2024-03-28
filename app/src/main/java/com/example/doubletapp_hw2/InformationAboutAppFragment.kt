package com.example.doubletapp_hw2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doubletapp_hw2.databinding.FragmentListHabitBinding
import androidx.fragment.app.Fragment
import com.example.doubletapp_hw2.databinding.FragmentInfoBinding

class InformationAboutAppFragment : Fragment() {

    private lateinit var binding : FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}