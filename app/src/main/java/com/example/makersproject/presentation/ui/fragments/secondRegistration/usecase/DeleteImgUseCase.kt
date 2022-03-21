package com.example.testmakers.ui.dashboard

import com.example.makersproject.presentation.ui.fragments.secondRegistration.model.ModelImg
import com.example.makersproject.presentation.ui.fragments.secondRegistration.repository.ImgRepository

class DeleteImgUseCase(private val repository: ImgRepository) {

    fun deleteImg(img: ModelImg) {
        repository.deleteImg(img)
    }
}