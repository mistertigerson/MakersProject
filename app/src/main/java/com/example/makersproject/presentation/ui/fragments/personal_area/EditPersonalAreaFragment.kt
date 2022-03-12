package com.example.makersproject.presentation.ui.fragments.personal_area

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.makersproject.databinding.FragmentEditPersonalAreaBinding

class EditPersonalAreaFragment : Fragment() {

    private lateinit var binding: FragmentEditPersonalAreaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditPersonalAreaBinding.inflate(layoutInflater)
        return binding.root
    }
}