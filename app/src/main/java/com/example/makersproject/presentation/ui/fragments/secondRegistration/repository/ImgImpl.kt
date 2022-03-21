package com.example.makersproject.presentation.ui.fragments.secondRegistration.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.makersproject.presentation.ui.fragments.secondRegistration.model.ModelImg

class ImgImpl : ImgRepository {

    private val galleryListLD = MutableLiveData<List<ModelImg>>()
    private val galleryList = sortedSetOf<ModelImg>({ o1, o2 -> o1.id.compareTo(o2.id) })
    private var autoIncrementId = 0

    override fun addImg(img: ModelImg) {
        if (img.id == ModelImg.UNDEFINED_ID) {
            img.id = autoIncrementId++
        }
        galleryList.add(img)
        galleryUpdateList()

    }

    override fun getImg(): LiveData<List<ModelImg>> {
        return galleryListLD
    }

    override fun deleteImg(img: ModelImg) {
        galleryList.remove(img)
        galleryUpdateList()
    }

    private fun galleryUpdateList() {
        galleryListLD.value = galleryList.toList()
    }
}