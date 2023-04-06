package com.fitri.jilbab.ui.splash

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.fitri.jilbab.MainActivity
import com.fitri.jilbab.data.local.SharedPref
import com.fitri.jilbab.databinding.ActivitySplashBinding
import com.fitri.jilbab.ui.admin.SuperActivity
import com.fitri.jilbab.ui.login.LoginActivity

class Splash : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val w = this.window
        w.statusBarColor = Color.parseColor("#FFFFFF")
        r_delay()
    }

    private fun r_delay() {
        val handler = Handler()
        handler.postDelayed({
            if (SharedPref.isLoggedIn) {
                if (SharedPref.userRole == "customer") {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, SuperActivity::class.java)
                    startActivity(intent)
                }
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

            SharedPref.idNav = 1

            finish()
        }, 3500)
    }


}