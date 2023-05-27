package com.fitri.jilbab.ui.checkout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.Helpers.formatPrice
import com.fitri.jilbab.data.model.user.newCo.Data
import com.fitri.jilbab.data.model.user.order.BodyPlaceOrder
import com.fitri.jilbab.databinding.ActivityCheckoutBinding
import com.fitri.jilbab.ui.cart.CartActivity
import com.fitri.jilbab.ui.cart.CartViewModel
import com.fitri.jilbab.ui.midtrans.MidtransActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CheckoutActivity : BaseActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private val viewModel: CartViewModel by viewModels()
    private var p_total: Int = 0
    private lateinit var data: Data

    private var courier: String? = ""
    private var courierPackage: String? = ""
    private var shippingCost: String? = ""
    private var deliveryEstimate: String? = ""
    private var idShippingAddress: String? = ""
    private var totalPrice: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        r_getIntent()
        setupObserver()

        binding.verifyAcc.setOnClickListener {
            val i = Intent(this, CartActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun r_getIntent() {
        val idexample = intent.getStringExtra("data").toString()
        lifecycleScope.launch {
            viewModel.checkout(idexample)
        }
    }

    override fun setupObserver() {
        loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) showLoading() else hideLoading()
        }
        viewModel.pay.observe(this) {
            Log.e("TAG", "setupObserver: " + it.data)
            data = it.data

            binding.rvOrder.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                adapter = itemAdapter(it.data.orders.toMutableList())
            }

            binding.tvName.text = it.data.shipping_address.name
            binding.tvPhoneNo.text = it.data.shipping_address.phone
            binding.tvAddress.text = it.data.shipping_address.address
            binding.tvDetail.text = it.data.shipping_address.detail_address
            idShippingAddress = it.data.shipping_address.id_ship_address.toString()

            for (i: Int in 0 until it.data.orders.size) {
                p_total =
                    p_total + ((it.data.orders[i].product.price.toInt()) * (it.data.orders[i].qty))
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
            val intent: Intent = Intent(this@CheckoutActivity, ShippingActivity::class.java)
            intent.putExtra("shipping", data.courier)
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 0) {
            if (resultCode === RESULT_OK) {
                val kurir = data!!.getStringExtra("kurir")
                val service = data!!.getStringExtra("service")
                val price = data!!.getStringExtra("price")
                Log.e("TAG", "onActivityResult: " + kurir + " " + service + " " + price)
                binding.textView11.text = kurir
                binding.tvTax.formatPrice(price.toString())

                val totalPlusOngkir = p_total + price!!.toInt()
                binding.tvTotal.formatPrice(totalPlusOngkir.toString())

                courier = kurir
                courierPackage = service
                shippingCost = price
                deliveryEstimate = data.getStringExtra("estimate")
                totalPrice = totalPlusOngkir.toString()
                binding.btnCheckout.setOnClickListener {
                    val i = Intent(this, MidtransActivity::class.java)
                    i.putExtra(
                        "midtrans",
                        BodyPlaceOrder(
                            courier = courier!!,
                            courier_package = courierPackage!!,
                            shipping_cost = shippingCost!!,
                            delivery_estimate = deliveryEstimate!!,
                            id_shipping_address = idShippingAddress!!,
                            total_price = totalPrice!!
                        )
                    )
                    startActivity(i)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this, CartActivity::class.java)
        startActivity(i)
        finish()
    }

}












