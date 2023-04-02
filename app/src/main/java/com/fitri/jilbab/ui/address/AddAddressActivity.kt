package com.fitri.jilbab.ui.address

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.databinding.ActivityAddAddressBinding

class AddAddressActivity : BaseActivity() {

    private lateinit var    binding             : ActivityAddAddressBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setupObserver() {
        TODO("Not yet implemented")
    }
}