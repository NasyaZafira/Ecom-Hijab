package com.fitri.jilbab.ui.checkout

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.Helpers.formatPrice
import com.fitri.jilbab.data.model.user.checkout.Courier
import com.fitri.jilbab.data.model.user.checkout.Data
import com.fitri.jilbab.data.model.user.checkout.Order
import com.fitri.jilbab.databinding.ActivityCheckoutBinding
import com.fitri.jilbab.ui.cart.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CheckoutActivity : BaseActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private val viewModel: CartViewModel by viewModels()
    private var             p_price       : Int = 0
    private var             p_qty       : Int = 0
    private var             p_total       : Int = 0
    private lateinit var data : Data



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.getParcelable<Data>("data").let {
            lifecycleScope.launch{
                //viewModel.checkout(data.shipping_address.id_ship_address.toString())
                viewModel.checkout(it?.shipping_address!!.id_ship_address.toString())
            }
        }

        binding.textView10.setOnClickListener {
            val i = Intent(this, ShippingActivity::class.java)
            startActivity(i)
        }
        setupObserver()
    }

    override fun setupObserver() {
       viewModel.pay.observe(this){
           data = it.data
           binding.rvOrder.apply {
               layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
               adapter = itemAdapter(it.data.orders.toMutableList())
           }
           binding.tvName.text = it.data.shipping_address.name
           binding.tvPhoneNo.text = it.data.shipping_address.phone
           binding.tvAddress.text = it.data.shipping_address.address
           binding.tvDetail.text = it.data.shipping_address.detail_address

           p_price = it.data.orders[0].product.price.toInt()
           p_qty = it.data.orders[0].qty
           p_total = p_price * (p_qty)

           binding.tvHarga.formatPrice(p_total.toString())
       }
    }
}


