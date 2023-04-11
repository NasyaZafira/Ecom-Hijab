package com.fitri.jilbab

import android.graphics.Rect
import android.hardware.lights.Light
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.fitri.jilbab.data.local.SharedPref
import com.fitri.jilbab.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navGraph: NavGraph


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        r_navBottom()
        r_statusBar()
    }
    private fun r_statusBar() {
        val window: Window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor       = ContextCompat.getColor(this, R.color.colorBase)
        window.navigationBarColor   = ContextCompat.getColor(this,R.color.colorBase)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }


    }

    private fun r_navBottom() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_example) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.bottomNav.setupWithNavController(navController)

        val graphInflater = navHostFragment.navController.navInflater
        navGraph = graphInflater.inflate(R.navigation.mobile_navigation)
        navController = navHostFragment.navController

        if (SharedPref.idNav == 1){
            navGraph.setStartDestination(R.id.nav_home)
        }else if (SharedPref.idNav == 2){
            navGraph.setStartDestination(R.id.nav_order)
        }else if (SharedPref.idNav == 3){
            navGraph.setStartDestination(R.id.nav_profile)
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