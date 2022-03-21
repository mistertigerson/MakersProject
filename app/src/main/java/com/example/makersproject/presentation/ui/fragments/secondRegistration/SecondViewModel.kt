package com.example.makersproject.presentation.ui.fragments.secondRegistration

import androidx.lifecycle.ViewModel
import com.example.makersproject.presentation.ui.fragments.secondRegistration.model.ModelImg
import com.example.makersproject.presentation.ui.fragments.secondRegistration.repository.ImgImpl
import com.example.makersproject.presentation.ui.fragments.secondRegistration.usecase.AddImgUseCase
import com.example.testmakers.ui.dashboard.DeleteImgUseCase
import com.example.testmakers.ui.dashboard.GetImgUseCase

class SecondViewModel:ViewModel() {

    private val repository = ImgImpl()

    private val addImgUseCase = AddImgUseCase(repository)

    private val getImgUseCase = GetImgUseCase(repository)

    private val deleteImgUseCase = DeleteImgUseCase(repository)

    val imgList = getImgUseCase.getImg()

    fun addImgItem(img: ModelImg) {
        addImgUseCase.addImg(img)
    }

    fun deleteImg(img: ModelImg) {
        deleteImgUseCase.deleteImg(img)
    }
}