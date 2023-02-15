package com.fitri.jilbab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fitri.jilbab.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var    navGraph        : NavGraph



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        r_navBottom()
    }

    private fun r_navBottom() {
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_example) as NavHostFragment
//        navController = navHostFragment.findNavController()
//        binding.bottomNav.setupWithNavController(navController)
//
//        val graphInflater = navHostFragment.navController.navInflater
//        navGraph = graphInflater.inflate(R.navigation.mobile_navigation)
//        navController = navHostFragment.navController
//        navController.graph = navGraph

        binding.bottomNav.itemIconTintList = null
        val navController = findNavController(R.id.nav_host_fragment_activity_example)
        binding.bottomNav.setupWithNavController(navController)
    }
}