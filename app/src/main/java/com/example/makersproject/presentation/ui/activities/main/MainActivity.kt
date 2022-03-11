package com.example.makersproject.presentation.ui.activities.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.makersproject.R
//import dagger.hilt.android.AndroidEntryPoint
import kotlin.text.Typography.dagger

//@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    private val binding: ActivityMainBinding by viewBinding()
//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostController=supportFragmentManager.findFragmentById(R.id.navHostFragment)

        val navController: NavController = navHostController!!.findNavController()

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.firstRegistrationFragment)
            .build()
        setupActionBarWithNavController(navController, appBarConfiguration)
//        binding.bottomNav.setupWithNavController(navController)

        //коментарий
    }
}