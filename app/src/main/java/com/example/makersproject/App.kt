package com.example.makersproject

import android.app.Application
//import dagger.hilt.android.HiltAndroidApp

//@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

//        appDataBase = Room
//            .databaseBuilder(this, AppDataBase::class.java, "model")
//            .fallbackToDestructiveMigration()
//            .build()
    }
//
//    companion object{
//        lateinit var appDataBase: AppDataBase
//    }
}