package com.fitri.jilbab.ui.home

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.commer.app.base.BaseActivity
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.fitri.jilbab.Helpers.formatPrice
import com.fitri.jilbab.data.model.admin.product.list.Data
import com.fitri.jilbab.data.model.user.Picture
import com.fitri.jilbab.databinding.ActivityDetailProductBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailProductActivity : BaseActivity() {

    private lateinit var    binding     : ActivityDetailProductBinding
    private val             viewModel   : HomeViewModel by viewModels()
    private lateinit var    data        : Data
    private var             dataPicture : MutableList<Picture> = ArrayList()
    private var             imageList   = ArrayList<SlideModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.icBack.setOnClickListener {
            finish()
        }

        intent.extras?.getParcelable<Data>("product")?.let{
            data = it

            binding.tvTitle.text    = it.product_name
            binding.tvDesc.text     = it.product_description
            binding.tvInfo.text     = it.product_detail_info
            binding.tvTotal.formatPrice(it.price.toString())
        }

        lifecycleScope.launch {
            Log.e("TAG", "onCreate: id product " + data.id_product )
            viewModel.productUser(data.id_product!!)
            setupObserver()
        }



    }

    override fun setupObserver() {
        viewModel.detailProduct.observe(this){
            dataPicture = it.data.pictures!!.toMutableList()
            Log.e("TAG", "setupObserver: data " + it )
            Log.e("TAG", "setupObserver: data picture " + dataPicture )

            for (i : Int in 0 until dataPicture.size){
                //imageList.add(SlideModel("https://ecom-mobile.spdev.my.id/img/products/" + it.data.pictures[i].picture , i.toString()))
                imageList.add(SlideModel("https://ecom-mobile.spdev.my.id/img/products/" + it.data.pictures[i].picture))
            }
            binding.ivPoster.setImageList(imageList,ScaleTypes.CENTER_CROP)
        }
    }



























}