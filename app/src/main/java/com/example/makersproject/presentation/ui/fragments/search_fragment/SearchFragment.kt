package com.example.makersproject.presentation.ui.fragments.search_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.makersproject.R
import com.example.makersproject.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    private  val  binding : FragmentSearchBinding by viewBinding()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}