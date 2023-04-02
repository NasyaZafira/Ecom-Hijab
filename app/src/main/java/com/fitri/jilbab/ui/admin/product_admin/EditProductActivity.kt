package com.fitri.jilbab.ui.admin.product_admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.databinding.ActivityAddAddressBinding
import com.fitri.jilbab.databinding.ActivityEditProductBinding

class EditProductActivity : BaseActivity() {

    private lateinit var    binding             : ActivityEditProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setupObserver() {
        TODO("Not yet implemented")
    }
}