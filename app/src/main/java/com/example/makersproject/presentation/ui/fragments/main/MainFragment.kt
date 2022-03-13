package com.example.makersproject.presentation.ui.fragments.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.makersproject.R
import com.example.makersproject.databinding.FragmentMainBinding


class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding()
    private lateinit var adapter: MainAdapter
    private var list: ArrayList<MainModel> = ArrayList()
    private lateinit var mainModel: MainModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainModel = MainModel(
            R.drawable.common_google_signin_btn_icon_dark,
            "Java",
            "david trezege",
            "ahhahahah"
        )
        list.add(mainModel)
        list.add(mainModel)
        list.add(mainModel)
        list.add(mainModel)
        list.add(mainModel)

        adapter = MainAdapter(list, object : MainAdapter.ClickOnPlaylist2 {
            override fun onClick() {
                findNavController().navigate(R.id.detailsFragment)
            }
        })
        binding.recyclerMain.adapter = adapter
    }
}