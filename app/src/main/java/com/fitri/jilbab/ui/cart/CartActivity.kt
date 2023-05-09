package com.fitri.jilbab.ui.cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.MainActivity
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.user.cart.list.Cart
import com.fitri.jilbab.databinding.ActivityCartBinding
import com.fitri.jilbab.ui.checkout.CheckoutActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartActivity : BaseActivity() {

    private lateinit var binding: ActivityCartBinding
    private val viewModel: CartViewModel by viewModels()
    private var adapterCart =
        CartAdapter(mutableListOf(), onRemove = { data, position -> removeCart(data, position) })
    private var id_example: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(R.style.Theme_FitriJilbab_Home)

        binding.verifyAcc.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
        launch()
        button()

    }

    private fun launch() {
        lifecycleScope.launch {
            viewModel.cartList()
        }
        setupObserver()
    }

    private fun removeCart(content: Cart, position: Int) {
        lifecycleScope.launch {
            viewModel.removeCart(content.id_cart)
        }
    }

    override fun setupObserver() {
        loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) showLoading() else hideLoading()
        }
        viewModel.cart.observe(this) { response ->
            binding.txtNull.visibility = View.INVISIBLE
            binding.rvCart.visibility = View.VISIBLE

            Log.e("TAG", "setupObserver: " + response)
            adapterCart.cart.clear()
            adapterCart.cart.addAll(response.data.cart)
            adapterCart.notifyDataSetChanged()
            binding.rvCart.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                adapter = adapterCart
            }

            if (response.data.shipping_address.isNotEmpty()) {
                //id_example = it.data.shipping_address[0].id_ship_address.toString()
                for (i: Int in 0 until response.data.shipping_address.size) {
                    if (response.data.shipping_address[i].is_main_address == 1) {
                        id_example =
                            response.data.shipping_address[i].id_ship_address.toString()
                        Log.e("TAG", "setupObserver: uji coba " + id_example)
                    }
                }

            }


        }
        viewModel.pay.observe(this) {
            Log.e("TAG", "\n INI CHECKOUT: " + it)

        }
        viewModel.remove.observe(this) {
            //adapterCart.cart.clear()
            //adapterCart.notifyDataSetChanged()
            //binding.rvCart.apply {
            //    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
            //    adapter = adapterCart
            //}
            lifecycleScope.launch {
                viewModel.cartList()
            }
        }
    }

    private fun button() {
        binding.btnCheckout.setOnClickListener {
            //            val i = Intent(this, MidtransActivity::class.java)
            //            startActivity(i)
            val i = Intent(this, CheckoutActivity::class.java)
            i.putExtra("data", id_example)
            startActivity(i)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

}


