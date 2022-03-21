package com.example.testmakers.ui.dashboard

import com.example.makersproject.presentation.ui.fragments.secondRegistration.repository.ImgRepository


class GetImgUseCase(private val repository: ImgRepository) {

    fun getImg()=repository.getImg()

}