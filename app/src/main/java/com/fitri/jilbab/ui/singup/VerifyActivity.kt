package com.fitri.jilbab.ui.singup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fitri.jilbab.databinding.ActivityVerifyBinding
import com.fitri.jilbab.ui.login.LoginActivity

class VerifyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnContinue.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finishAffinity()
        }
        binding.verifyAcc.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}