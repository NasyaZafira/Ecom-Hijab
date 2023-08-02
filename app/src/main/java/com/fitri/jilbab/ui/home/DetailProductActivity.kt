package com.fitri.jilbab.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.Helpers.formatPrice
import com.fitri.jilbab.R
import com.fitri.jilbab.base.BaseActivity
import com.fitri.jilbab.data.local.SharedPref
import com.fitri.jilbab.data.model.user.latestDt.Data
import com.fitri.jilbab.data.model.user.latestDt.Picture
import com.fitri.jilbab.databinding.ActivityDetailProductBinding
import com.fitri.jilbab.ui.admin.product_admin.EditProductActivity
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
    private var reviewAdapter = ReviewAdapter(mutableListOf())
    private var listSpinner: MutableList<String> = ArrayList()
    private var idValue: String = " "
    private var p_colors: MutableList<String> = ArrayList()
    private var p_stok: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        f_admin()
        f_extras()
        f_reting()
        f_back()
        f_total()
//        f_troli()


    }

    private fun f_admin() {

    }

    private fun f_reting() {
        // Create rating bar programmatically...
        val ratingBar = RatingBar(this)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(30, 30, 30, 30)
        ratingBar.layoutParams = layoutParams
        ratingBar.stepSize = 1.0.toFloat()

        // Add RatingBar to LinearLayout
        binding.rootContainer.addView(ratingBar)

        binding.txtSend.setOnClickListener {
            val ulasan = binding.edtKoment.text.toString().trim()
            val rating = ratingBar.rating.toInt().toString()
            val id = data.id_product

            Log.e("TAG", "f_reting: -" + ulasan + "-" + rating + "-" + id)

            if (!ulasan.isNullOrBlank() && !rating.isNullOrBlank() && id != null) {
                binding.edtKoment.text = null
                lifecycleScope.launch {
                    viewModel.reviewProduct(
                        id.toString(),
                        rating,
                        ulasan
                    )
                }
                Log.e("TAG", "f_reting: compleate")
            } else {
                Toast.makeText(this, "Lengkapi rating dan ulasan", Toast.LENGTH_LONG).show()
                Log.e("TAG", "f_reting: not compleate")
            }

        }
    }

    private fun f_extras() {
        val id = intent.getLongExtra("product", 0)
        f_launch(id.toInt())
        f_listReview(id.toString())
    }

    private fun f_listReview(id_Product: String) {
        lifecycleScope.launch {
            viewModel.listReview(id_Product)
        }
    }

    private fun f_launch(id_Product: Int) {
        lifecycleScope.launch {
            //Log.e("TAG", "onCreate: id product " + data.id_product )
            viewModel.productUser(id_Product)
            setupObserver()
        }
    }

    private fun f_back() {
        binding.icBack.setOnClickListener {
            finish()
        }
    }

    override fun setupObserver() {
        loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) showLoading() else hideLoading()
        }

        viewModel.detailProduct.observe(this) {
            dataPicture = it.data.pictures.toMutableList()
            Log.e("TAG", "setupObserver: data " + it)
            Log.e("TAG", "setupObserver: data picture " + dataPicture)


            for (i: Int in 0 until dataPicture.size) {
                //imageList.add(SlideModel("https://ecom-mobile.spdev.my.id/img/products/" + it.data.pictures[i].picture , i.toString()))
                imageList.add(SlideModel("https://ecom-mobile.spdev.my.id/img/products/" + it.data.pictures[i].picture))
            }
            binding.ivPoster.setImageList(imageList, ScaleTypes.CENTER_CROP)

            data = it.data
            binding.ratingBar.rating = it.data.rating.toFloat()
            binding.tvTitle.text = it.data.product_name
            binding.tvDesc.text = it.data.product_description
//            binding.tvInfo.text = it.data.product_detail_info
            p_stok = it.data.stock


            if (SharedPref.userRole == "superuser") {
                binding.btnOrderNow.visibility = View.INVISIBLE
                binding.addCount.visibility = View.INVISIBLE
                binding.count.visibility = View.INVISIBLE
                binding.removeCount.visibility = View.INVISIBLE
                binding.textView8.text = "Jumlah Stok : " + it.data.stock

                binding.tvTotal.visibility = View.INVISIBLE
                binding.btnEditProduct.visibility = View.VISIBLE
                binding.btnEditProduct.setOnClickListener {
                    val i = Intent(this, EditProductActivity::class.java)
                    i.putExtra("productDetail", data)
                    startActivity(i)
                    finish()
                }
            }


            binding.tvInfo.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(it.data.product_detail_info, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(it.data.product_detail_info)
            }
            binding.txtPrice.formatPrice(it.data.price.toString())
            if (it.data.discount.isNullOrBlank()) {
                binding.txtDisc.visibility = View.INVISIBLE
            } else {
                binding.txtDisc.text = "- " + it.data.discount + "%"
            }
            binding.tvTotal.formatPrice("0")
            val result = it.data.color_list.toMutableList()
            for (i: Int in 0 until result.size) {
                listSpinner.add(it.data.color_list[i])
//                p_stok.add(it.data.stok_color_list[i])

                val arrayAdapterKategori = ArrayAdapter(this, R.layout.signup_menu, listSpinner)
                val autoCompleteKategori = binding.isColor
                autoCompleteKategori.setAdapter(arrayAdapterKategori)

                binding.isColor.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
                    idValue = listSpinner.get(position)
                    Log.e("TAG", "f_listSpinner: " + listSpinner.get(position))

                    if (position != null) {
                        if (it.data.stok_color_list.get(position) < 1.toString()) {
                            binding.nullColor.visibility = View.VISIBLE
                            binding.nullColor.setText(
//                                "Stok warna " + idValue + " tersedia " + it.data.stok_color_list.get(
//                                    position
//                                ) + " pcs"
                                "Stok warna " + idValue + " habis"
                            )

//                            Toast.makeText(this, "Stok Warna Kosong", Toast.LENGTH_LONG).show()
//                            Log.e("TAG", "setupObserver: " + idValue)
                        } else {
                            binding.nullColor.visibility = View.VISIBLE
                            binding.nullColor.setText(
                                "Stok warna " + idValue + " tersedia " + it.data.stok_color_list.get(
                                    position
                                ) + " pcs"
                            )

                        }
                    }

                })
            }

            if (data.stock < 1.toString()) {
                binding.btnHabis.visibility = View.VISIBLE
                binding.btnOrderNow.visibility = View.INVISIBLE
//                Toast.makeText(this, "Stok Habis", Toast.LENGTH_LONG).show()
            } else {
                f_troli()
            }
        }

        cartViewModel.add.observe(this) {
            Log.e("TAG", "setupObserver: " + it)
            val i = Intent(this, CartActivity::class.java)
            startActivity(i)
            finish()
        }

        viewModel.isReview.observe(this) {
            Toast.makeText(this, "Review dikirim", Toast.LENGTH_LONG).show()
            f_listReview(it.data.id_product)
//            val i = Intent(this, MainActivity::class.java)
//            startActivity(i)
//            finish()
            Log.e("TAG", "setupObserver: " + it)
        }
        viewModel.listReview.observe(this) {
            reviewAdapter.review.clear()
            reviewAdapter.review.addAll(it.data)
            reviewAdapter.notifyDataSetChanged()
            binding.rvProduct.apply {
                layoutManager = LinearLayoutManager(
                    context, RecyclerView.VERTICAL,
                    true
                )
                adapter = reviewAdapter
            }
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
        binding.btnHabis.visibility = View.INVISIBLE
        binding.btnOrderNow.visibility = View.VISIBLE
        binding.btnOrderNow.setOnClickListener {
            if (p_total == 0) {
                Log.e("TAG", "f_troli: total 0 please add min 1 order")
                Toast.makeText(this, "Mohon Tambahkan Produk Minimal 1 Order", Toast.LENGTH_LONG)
                    .show()
            } else if (p_total > 0) {
                p_name = data.product_name.toString()
                p_total = p_total
                p_price = p_total * (data.price!!.toInt())
                Log.e("TAG", "f_troli: p_name " + p_name)
                Log.e("TAG", "f_troli: p_total " + p_total)
                Log.e("TAG", "f_troli: p_price " + p_price)
                val color = idValue
                val id = data.id_product.toString()
                val total = p_total.toString()
                if (color.isNullOrBlank()) {
                    Toast.makeText(
                        this,
                        "Mohon Pilih Varian Warna Terlebih Dahulu",
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else if (p_total.toString() > p_stok) {
                    Toast.makeText(
                        this,
                        "Jumlah melebihi ketersediaan, produk tersisa $p_stok",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (!id.isNullOrBlank() && !color.isNullOrBlank() && !total.isNullOrBlank()) {
                    lifecycleScope.launch {
                        cartViewModel.addCart(id, total, color)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}

















