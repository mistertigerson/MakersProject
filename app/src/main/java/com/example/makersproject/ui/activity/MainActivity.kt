package com.example.makersproject.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.makersproject.R
import com.example.makersproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {



    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostController = supportFragmentManager.findFragmentById(R.id.navHostFragment)

        val navController: NavController = navHostController!!.findNavController()

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.firstRegistrationFragment
        )
            .build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNav.setupWithNavController(navController)
    }
}