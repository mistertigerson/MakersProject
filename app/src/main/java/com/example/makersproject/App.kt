package com.example.makersproject

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
//import androidx.room.Room
//import com.example.makersproject.data.local.AppDataBase
import dagger.hilt.android.HiltAndroidApp


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        fbAuth = FirebaseAuth.getInstance()
        fireStore = FirebaseFirestore.getInstance()
    }

    companion object{
        lateinit var fbAuth: FirebaseAuth
        lateinit var fireStore: FirebaseFirestore
    }
}