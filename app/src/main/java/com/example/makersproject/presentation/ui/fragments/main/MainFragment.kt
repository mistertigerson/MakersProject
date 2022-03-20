package com.example.makersproject.presentation.ui.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
//        val name: String? =  args.name
//        val pass : String? =  args.password
        mainModel = MainModel(
            R.drawable.common_google_signin_btn_icon_dark,
           "relax",
           "take it easy",
            "ahhahahah"
        )

        list.add(mainModel)
        list.add(mainModel)
        list.add(mainModel)
        list.add(mainModel)
        adapter = MainAdapter(object : MainAdapter.ClickOnPlaylist2 {
            override fun onClick(model: MainModel, position: Int) {
                val bundle: Bundle = Bundle()
                val bundle2: Bundle = Bundle()

                MainFragmentDirections.actionMainFragmentToDetailsFragment(list[position].title)
                bundle.putSerializable(TITLE, list[position].title)
                bundle2.putString("sexy", list[position].title)
//                Log.e("TAG", "list: ${bundle2.getString("sexy")}")
                Log.e("TAG", "list: ${MainFragmentDirections.actionMainFragmentToDetailsFragment(list[position].title)}")
//                bundle.putSerializable("author", list[position].NameOfAuthor)
//                bundle.putSerializable("key", list[position].comments)
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