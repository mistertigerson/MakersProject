package com.example.makersproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
//import com.example.makersproject.data.models.User

//@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

//    abstract fun userDao(): UserDao
}