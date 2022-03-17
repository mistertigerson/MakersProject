package com.example.makersproject.presentation.ui.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.makersproject.R
import com.example.makersproject.databinding.FragmentMainBinding
import com.example.makersproject.presentation.ui.fragments.firstRegistration.FirstRegistrationFragment
import io.grpc.NameResolver
import kotlin.math.log


class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding()
    private lateinit var adapter: MainAdapter
    private var list: ArrayList<MainModel> = ArrayList()
    private lateinit var mainModel: MainModel
    private val args: MainFragmentArgs by navArgs()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainModel = MainModel(
            R.drawable.common_google_signin_btn_icon_dark,
            args.name.toString(),
            args.password.toString(),
            "ahhahahah"
        )

        list.add(mainModel)
        list.add(mainModel)
        list.add(mainModel)
        list.add(mainModel)
        adapter = MainAdapter(object : MainAdapter.ClickOnPlaylist2 {
            override fun onClick(model: MainModel, position: Int) {
                val bundle: Bundle = Bundle()
                bundle.putSerializable("title", list[position].title)
                Log.e("TAG", "list: ${list[position].title}", )
                bundle.putSerializable("author", list[position].NameOfAuthor)
                bundle.putSerializable("key", list[position].comments)
                findNavController().navigate(R.id.detailsFragment, bundle)
            }

            override fun clickBtn() {
                findNavController().navigate(R.id.authorizationFragment)
            }
        })
        binding.recyclerMain.adapter = adapter
        adapter.setList(list)

    }

    companion object{
        const val TITLE = "title"
        const val AUTHOR = "author"
        const val COMMENTS = "comments"

    }
}