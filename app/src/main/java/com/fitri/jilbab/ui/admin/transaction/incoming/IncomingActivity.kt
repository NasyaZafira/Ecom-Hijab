package com.fitri.jilbab.ui.admin.transaction.incoming

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.databinding.ActivityCheckoutBinding
import com.fitri.jilbab.databinding.ActivityIncomingBinding
import com.fitri.jilbab.ui.admin.SuperActivity
import com.fitri.jilbab.ui.cart.CartActivity
import com.fitri.jilbab.ui.product.OrderViewModel
import com.fitri.jilbab.ui.product.incoming.Incomingadapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IncomingActivity : BaseActivity() {

    private lateinit var    binding     : ActivityIncomingBinding
    private val viewModel: OrderViewModel by viewModels()
//    private var incAdmin = AincomAdapter(mutableListOf(), { data, position ->
//        intentToHoax(data, position)} )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncomingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.verifyAcc.setOnClickListener {
            val i = Intent(this, SuperActivity::class.java)
            startActivity(i)
            finish()
        }
    }

//    private fun intentToHoax(content: Output, position: Int) {
//        Log.e("TAG", "intentToHoax: " + content.url)
//
//        lifecycleScope.launch {
//
//        }
//
//    }

    override fun setupObserver() {
        TODO("Not yet implemented")
    }
}