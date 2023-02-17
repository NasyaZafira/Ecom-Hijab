package com.fitri.jilbab.ui.address

import android.os.Bundle
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.databinding.ActivityListAddressBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListAddressActivity : BaseActivity() {
    private lateinit var binding: ActivityListAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setupObserver() {

    }
}