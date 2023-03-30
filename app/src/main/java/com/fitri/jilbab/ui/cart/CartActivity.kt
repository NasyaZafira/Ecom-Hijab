package com.fitri.jilbab.ui.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.MainActivity
import com.fitri.jilbab.data.model.user.cart.Data
import com.fitri.jilbab.databinding.ActivityCartBinding
import com.fitri.jilbab.databinding.ActivitySigninBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartActivity : BaseActivity() {

    private lateinit var binding    : ActivityCartBinding
    private val viewModel           : CartViewModel by viewModels()
    private var adapterCart = CartAdapter(mutableListOf(),  onRemove = { data, position -> removeCart(data,position)})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.cartList()
        }

        setupObserver()

    }

    private fun removeCart(content: Data, position: Int){
        lifecycleScope.launch {
            viewModel.removeCart(content.id_cart)
        }
    }

    override fun setupObserver() {
        loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) showLoading() else hideLoading()
        }
        viewModel.cart.observe(this){
            Log.e("TAG", "setupObserver: " + it )
            adapterCart.cart.clear()
            adapterCart.cart.addAll(it.data)
            adapterCart.notifyDataSetChanged()
            binding.rvCart.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                adapter = adapterCart

            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this,MainActivity::class.java)
        startActivity(i)
        finish()
    }

}


