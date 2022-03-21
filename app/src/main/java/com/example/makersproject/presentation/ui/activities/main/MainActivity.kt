package com.example.makersproject.presentation.ui.activities.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.makersproject.App
import com.example.makersproject.R
import com.example.makersproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private lateinit var navController: NavController
    private val fragments = arrayListOf(
        R.id.mainFragment,
        R.id.searchFragment,
        R.id.accountFragment,
        R.id.educationFragment
    )

    //проверка на то что есть такой юзер или нет
    override fun onStart() {
        super.onStart()
        if (App.fbAuth.currentUser == null){
            navController.navigate(R.id.firstRegistrationFragment)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavController()
    }

    private fun initNavController() {

        val navHostController = supportFragmentManager.findFragmentById(R.id.navHostFragment)
        navController = navHostController!!.findNavController()
        binding.bottomNav.setupWithNavController(navController)

        //скрыть bottomNavigation
        navController.addOnDestinationChangedListener {controller, destination, arguments ->
            if (fragments.contains(destination.id)) {
                binding.bottomNav.visibility = View.VISIBLE
            } else binding.bottomNav.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        Log.e("TAG", "onBackPressed: ", )
        navController.navigateUp()

    }
}