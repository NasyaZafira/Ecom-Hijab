package com.fitri.jilbab

import android.graphics.Rect
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
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.fitri.jilbab.data.local.SharedPref
import com.fitri.jilbab.databinding.ActivityMainBinding
import com.fitri.jilbab.ui.home.HomeFragment
import com.fitri.jilbab.ui.product.OrderFragment
import com.fitri.jilbab.ui.profile.ProfileFragment
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
//        r_navBottom()
        r_statusBar()
        newNavBot()
    }

    private fun newNavBot() {
        val firstFragment: Fragment = HomeFragment()
        val secondFragment: Fragment = OrderFragment()
        val thirdFragment: Fragment = ProfileFragment()


        var active = firstFragment

        supportFragmentManager.beginTransaction()
            .add(R.id.nav_host_fragment_activity_example, secondFragment, "2").hide(secondFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.nav_host_fragment_activity_example, thirdFragment, "3").hide(thirdFragment)
            .commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.nav_host_fragment_activity_example, firstFragment, "1").commit()

        binding.navBottom.setItemSelected(R.id.nav_home, true)

        binding.navBottom.setOnItemSelectedListener {
            when (it) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction().hide(active).show(firstFragment)
                        .commit()
                    active = firstFragment
                }
                R.id.nav_order -> {
                    supportFragmentManager.beginTransaction().hide(active).show(secondFragment)
                        .commit()
                    active = secondFragment
                }
                R.id.nav_profile -> {
                    supportFragmentManager.beginTransaction().hide(active).show(thirdFragment)
                        .commit()
                    active = thirdFragment
                }
            }
        }
    }

    private fun r_statusBar() {
        val window: Window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBase)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.colorBase)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }


    }


    private fun r_navBottom() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_example) as NavHostFragment
        navController = navHostFragment.findNavController()
//        binding.mewoNav.setupWithNavController(navController)

        val graphInflater = navHostFragment.navController.navInflater
        navGraph = graphInflater.inflate(R.navigation.mobile_navigation)
        navController = navHostFragment.navController

        if (SharedPref.idNav == 1) {
            navGraph.setStartDestination(R.id.nav_home)
        } else if (SharedPref.idNav == 2) {
            navGraph.setStartDestination(R.id.nav_order)
        } else if (SharedPref.idNav == 3) {
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