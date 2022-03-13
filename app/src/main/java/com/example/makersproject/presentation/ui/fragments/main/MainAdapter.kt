package com.example.makersproject.presentation.ui.fragments.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.makersproject.databinding.MainItemBinding

class MainAdapter(private val list: ArrayList<MainModel>, private val clickOnPlaylist2: ClickOnPlaylist2) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {


    private lateinit var binding : MainItemBinding



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        binding = MainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MainAdapter.MainViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class MainViewHolder(itemView: MainItemBinding) : RecyclerView.ViewHolder(itemView.root) {

        fun onBind(mainModel: MainModel) {
            binding.ivIcon.setImageResource(mainModel.imageIcon)
            binding.tvComments.text = mainModel.comments
            binding.tvNameOfAuthor.text = mainModel.NameOfAuthor
            binding.tvTitle.text = mainModel.title

            binding.root.setOnClickListener{
                clickOnPlaylist2.onClick()
            }
        }

    }

    interface ClickOnPlaylist2 {
        fun onClick()
    }
}