package com.fitri.jilbab.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.commer.app.base.BaseActivity
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.Helpers.formatPrice
import com.fitri.jilbab.data.model.user.Data

import com.fitri.jilbab.data.model.user.Picture
import com.fitri.jilbab.databinding.ActivityDetailProductBinding
import com.fitri.jilbab.ui.cart.CartActivity
import com.fitri.jilbab.ui.cart.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailProductActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailProductBinding
    private val viewModel: HomeViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var data: Data
    private var dataPicture: MutableList<Picture> = ArrayList()
    private var imageList = ArrayList<SlideModel>()

    private var p_name: String = ""
    private var p_total: Int = 0
    private var p_price: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        f_back()
        f_extras()
        f_total()
        f_troli()

    }

    private fun f_back() {
        binding.icBack.setOnClickListener {
            finish()
        }
    }

    private fun f_extras() {
        val id = intent.getLongExtra("product", 0)
        f_launch(id.toInt())

        //?.let{
//            data = it
//
//            binding.tvTitle.text    = it.product_name
//            binding.tvDesc.text     = it.product_description
//            binding.tvInfo.text     = it.product_detail_info
//            binding.txtPrice.formatPrice(it.price.toString())
//            binding.tvTotal.formatPrice("0")
//        }
    }

    private fun f_launch(id_Product: Int) {
        lifecycleScope.launch {
//            Log.e("TAG", "onCreate: id product " + data.id_product )
            viewModel.productUser(id_Product)
            setupObserver()
        }
    }

    override fun setupObserver() {
        loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) showLoading() else hideLoading()
        }
        viewModel.detailProduct.observe(this) {
            dataPicture = it.data.pictures!!.toMutableList()
            Log.e("TAG", "setupObserver: data " + it)
            Log.e("TAG", "setupObserver: data picture " + dataPicture)

            for (i: Int in 0 until dataPicture.size) {
                //imageList.add(SlideModel("https://ecom-mobile.spdev.my.id/img/products/" + it.data.pictures[i].picture , i.toString()))
                imageList.add(SlideModel("https://ecom-mobile.spdev.my.id/img/products/" + it.data.pictures[i].picture))
            }
            binding.ivPoster.setImageList(imageList, ScaleTypes.CENTER_CROP)

            data = it.data
            binding.tvTitle.text = it.data.product_name
            binding.tvDesc.text = it.data.product_description
            binding.tvInfo.text = it.data.product_detail_info
            binding.txtPrice.formatPrice(it.data.price.toString())
            binding.tvTotal.formatPrice("0")
        }
        cartViewModel.add.observe(this) {
            Log.e("TAG", "setupObserver: " + it)
            val i = Intent(this, CartActivity::class.java)
            startActivity(i)
            finish()
        }


    }

    private fun f_total() {
        binding.addCount.setOnClickListener {
            p_total = p_total + 1
            f_textCount()
        }
        binding.removeCount.setOnClickListener {
            if (p_total.equals(0)) {
                f_textCount()
            } else if (p_total > 0) {
                p_total = p_total - 1
                f_textCount()
            }
        }
    }

    private fun f_textCount() {
        binding.count.text = p_total.toString()
        p_price = p_total * (data.price!!.toInt())
        binding.tvTotal.formatPrice(p_price.toString())
    }

    private fun f_troli() {
        binding.btnOrderNow.setOnClickListener {
            if (p_total == 0) {
                Log.e("TAG", "f_troli: total 0 please add min 1 order")
            } else if (p_total > 0) {
                p_name = data.product_name.toString()
                p_total = p_total
                p_price = p_total * (data.price!!.toInt())
                Log.e("TAG", "f_troli: p_name " + p_name)
                Log.e("TAG", "f_troli: p_total " + p_total)
                Log.e("TAG", "f_troli: p_price " + p_price)
                f_addCart()
            }
        }
    }

    private fun f_addCart() {
        lifecycleScope.launch {
            cartViewModel.addCart(data.id_product.toString(), p_total.toString())
        }
    }


}