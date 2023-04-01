package com.fitri.jilbab.ui.checkout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.Helpers.formatPrice
import com.fitri.jilbab.data.model.user.co.Data
import com.fitri.jilbab.databinding.ActivityCheckoutBinding
import com.fitri.jilbab.ui.cart.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CheckoutActivity : BaseActivity() {

    private lateinit var    binding     : ActivityCheckoutBinding
    private val             viewModel   : CartViewModel by viewModels()
    private var             p_total     : Int = 0
    private lateinit var    data        : Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        r_getIntent()
        setupObserver()
    }

    private fun r_getIntent() {
        val idexample   = intent.getStringExtra("data").toString()
        lifecycleScope.launch {
            viewModel.checkout(idexample)
        }
    }

    override fun setupObserver() {
        viewModel.pay.observe(this){

            data = it.data

            binding.rvOrder.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                adapter = itemAdapter(it.data.orders.toMutableList())
            }

            binding.tvName.text      = it.data.shipping_address.name
            binding.tvPhoneNo.text   = it.data.shipping_address.phone
            binding.tvAddress.text   = it.data.shipping_address.address
            binding.tvDetail.text    = it.data.shipping_address.detail_address

            for (i : Int in 0 until it.data.orders.size){
                p_total = p_total +  ( (it.data.orders[i].product.price.toInt()) * (it.data.orders[i].qty) )
            }

            binding.tvHarga.formatPrice(p_total.toString())

            Log.e("TAG", "setupObserver:  JNE" + it.data.courier.jne)
            Log.e("TAG", "setupObserver:  JNE" + it.data.courier.jne)
            Log.e("TAG", "setupObserver:  JNE" + it.data.courier.jne)

            r_midranche()
        }
    }

    private fun r_midranche() {
        binding.textView10.setOnClickListener {
            val intent : Intent = Intent(this@CheckoutActivity, ShippingActivity::class.java)
            intent.putExtra("shipping", data.courier)
            startActivityForResult(intent , 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 0) {
            if (resultCode === RESULT_OK) {
                val kurir   = data!!.getStringExtra("kurir")
                val service = data!!.getStringExtra("service")
                val price   = data!!.getStringExtra("price")
                Log.e("TAG", "onActivityResult: " + kurir + " " + service + " " + price)
                binding.textView11.text = kurir
                binding.tvTax.text      = price

                val totalPlusOngkir = p_total + price!!.toInt()
                binding.tvTotal.formatPrice(totalPlusOngkir.toString())
            }
        }
    }


}












