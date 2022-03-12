package com.example.makersproject.presentation.ui.fragments.personal_area

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.makersproject.R
import com.example.makersproject.databinding.FragmentPersonalAreaBinding

class PersonalAreaFragment : Fragment() {

    private lateinit var binding: FragmentPersonalAreaBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPersonalAreaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnEdit.setOnClickListener {
            openFragment()
        }
    }

    private fun openFragment() {
        navController = findNavController()
        navController.navigate(R.id.editPersonalAreaFragment)
    }

}