package com.example.makersproject.presentation.ui.fragments.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.makersproject.databinding.FragmentEditAccountBinding
import com.example.makersproject.presentation.ui.fragments.account.constants.Constants

class EditAccountFragment : Fragment() {

    private lateinit var binding: FragmentEditAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etAddName.setOnClickListener {
            if (binding.etAddName.toString().isNotEmpty()) {
                val bundle = Bundle()
                bundle.putString(Constants.keyName, binding.etAddName.toString())
            }
        }
    }
}