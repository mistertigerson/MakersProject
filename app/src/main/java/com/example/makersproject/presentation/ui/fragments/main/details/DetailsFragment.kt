package com.example.makersproject.presentation.ui.fragments.main.details

import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.makersproject.databinding.FragmentDetailsBinding
import com.example.makersproject.presentation.ui.fragments.main.MainFragment.Companion.TITLE


class DetailsFragment : Fragment() {

    private val binding: FragmentDetailsBinding by viewBinding()
   // private val args: DetailsFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     //   val name: String = args.title.toString()
        val s = arguments?.getString("title")
        binding.tvTitle.text = s
        Log.e("TAG", "onViewCreated: ${s.toString()}", )

    }
}