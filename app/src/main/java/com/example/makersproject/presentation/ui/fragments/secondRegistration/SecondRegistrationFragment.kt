package com.example.makersproject.presentation.ui.fragments.secondRegistration

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.makersproject.R
import com.example.makersproject.databinding.FragmentSecondRegistrationBinding
import com.example.makersproject.presentation.ui.fragments.secondRegistration.model.ModelImg


class SecondRegistrationFragment : Fragment(R.layout.fragment_second_registration) {
    private val binding: FragmentSecondRegistrationBinding by viewBinding()
    private val viewModel: SecondViewModel by viewModels()
    private lateinit var adapterSecond: SecondAdapter

    private val registerForActivitySecond =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                if (it != null) {
                    val count: Int = it.data?.clipData!!.itemCount
                    for (i in 0 until count) {
                        viewModel.addImgItem(ModelImg(it.data?.clipData?.getItemAt(i)?.uri.toString()))
                    }
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initObservers()
        initListeners()
    }

    private fun pickImg() {
        Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        ).apply {
            type = "image/*"
            registerForActivity.launch(this)
        }
    }

    private fun initObservers() {
        viewModel.imgList.observe(viewLifecycleOwner) {
            adapterSecond.submitList(it)
        }
    }

    private fun initRecycler() {
        binding.recyclerSecond.apply {
            adapterSecond = SecondAdapter()
            adapter = adapterSecond
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private val registerForActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val filePath: Uri = it.data!!.data!!
                Glide.with(requireContext()).load(filePath).into(binding.imgSecondFragment)
            }
        }

    private fun pickCertificate() {
        Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        ).apply {
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            type = "image/*"
            registerForActivitySecond.launch(this)
        }
    }

    private fun initListeners() {
        adapterSecond.imgOnLongListener = {
            viewModel.deleteImg(it)
        }
        binding.btnAdd.setOnClickListener {
            pickCertificate()
        }

        binding.imgSecondFragment.setOnClickListener {
            pickImg()
        }

    }
}