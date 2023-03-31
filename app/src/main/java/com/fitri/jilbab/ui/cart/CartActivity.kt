package com.fitri.jilbab.ui.cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.MainActivity
import com.fitri.jilbab.data.model.user.cart.list.Cart
import com.fitri.jilbab.data.model.user.checkout.Data
import com.fitri.jilbab.databinding.ActivityCartBinding
import com.fitri.jilbab.ui.checkout.CheckoutActivity
import com.fitri.jilbab.ui.midtrans.MidtransActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartActivity : BaseActivity() {

    private lateinit var    binding             : ActivityCartBinding
    private val             viewModel           : CartViewModel by viewModels()
    private var             adapterCart         = CartAdapter(mutableListOf(),  onRemove = { data, position -> removeCart(data,position)})
    private var             id_example          : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launch()
        button()

        launch2()


    }

    private fun launch() {
        lifecycleScope.launch {
            viewModel.cartList()
        }
        setupObserver()
    }

    private fun launch2() {
        lifecycleScope.launch{
            //viewModel.checkout(data.shipping_address.id_ship_address.toString())
            viewModel.checkout(id_example)
        }
    }

    private fun removeCart(content: Cart, position: Int){
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
            adapterCart.cart.addAll(it.data.cart)
            adapterCart.notifyDataSetChanged()
            binding.rvCart.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                adapter = adapterCart
            }

            if (it.data.shipping_address.isNotEmpty()) {
                id_example = it.data.shipping_address[0].id_ship_address.toString()
            }
        }
        viewModel.pay.observe(this){
            Log.e("TAG", "\n INI CHECKOUT: " + it)
//            val i = Intent(this, CheckoutActivity::class.java)
//            startActivity(i)
//            finish()
        }
    }

    private fun button() {
        binding.btnCheckout.setOnClickListener {
            val i = Intent(this, MidtransActivity::class.java)
            startActivity(i)
//            val i = Intent(this, CheckoutActivity::class.java)
//            i.putExtra("data", id_example)
//            startActivity(i)
            finish()
//            lifecycleScope.launch{
//                viewModel.checkout(id_example)
//            }
        }
        setupObserver()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this,MainActivity::class.java)
        startActivity(i)
        finish()
    }

}


