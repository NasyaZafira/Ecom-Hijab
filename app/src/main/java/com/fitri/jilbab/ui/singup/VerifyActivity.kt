package com.fitri.jilbab.ui.singup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fitri.jilbab.databinding.ActivityVerifyBinding

class VerifyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}