package com.fitri.jilbab.ui.admin

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fitri.jilbab.R
import com.fitri.jilbab.data.local.SharedPref
import com.fitri.jilbab.databinding.ActivitySuperBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuperActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuperBinding
    private lateinit var navController: NavController
    private lateinit var navGraph: NavGraph
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        r_navBottom()
    }
    private fun r_navBottom() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_admin) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.bottomNavAdmin.setupWithNavController(navController)

        val graphInflater = navHostFragment.navController.navInflater
        navGraph = graphInflater.inflate(R.navigation.admin_navigation)
        navController = navHostFragment.navController

        if (SharedPref.navAdmin == 1){
            navGraph.setStartDestination(R.id.nav_home_admin)
        }else if (SharedPref.navAdmin == 2){
            navGraph.setStartDestination(R.id.nav_product_admin)
        }else if (SharedPref.navAdmin == 3){
            navGraph.setStartDestination(R.id.nav_report_admin)
        }
        navController.graph = navGraph
    }
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    Log.e("focus", "touchevent")
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
            if (v is AutoCompleteTextView) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    Log.e("focus", "touchevent")
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}