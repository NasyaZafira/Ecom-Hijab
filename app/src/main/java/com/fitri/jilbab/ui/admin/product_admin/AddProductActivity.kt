package com.fitri.jilbab.ui.admin.product_admin

import android.os.Bundle
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.databinding.ActivityAddProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductActivity : BaseActivity() {

    private lateinit var binding: ActivityAddProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun setupObserver() {
        TODO("Not yet implemented")
    }
}