package com.fitri.jilbab.ui.admin.transaction.complete

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fitri.jilbab.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.R
import com.fitri.jilbab.databinding.ActivityCompleteBinding
import com.fitri.jilbab.databinding.BottomSheetAdminBinding
import com.fitri.jilbab.ui.admin.SuperActivity
import com.fitri.jilbab.ui.product.OrderViewModel
import com.fitri.jilbab.ui.product.complete.CompleteAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompleteActivity : BaseActivity() {

    private lateinit var binding: ActivityCompleteBinding
    private val viewModel: OrderViewModel by viewModels()
    private lateinit var sheetDialog: BottomSheetDialog
    private lateinit var sheetBinding: BottomSheetAdminBinding

    @SuppressLint("SetTextI18n")
    private var theAdapter = AdCompleteAdapter(mutableListOf()){
        sheetDialog.apply {
            Log.e("TAG", "bottomsheet: show")
            if (!it.user.detail.profile_picture.isNullOrBlank()) {
                Glide.with(sheetBinding.cardFoto3.context)
                    .load("https://ecom-mobile.spdev.my.id/img/profile/" + it.user.detail.profile_picture)
                    .error(R.drawable.white_image)
                    .into(sheetBinding.cardFoto3)
            } else {
                Glide.with(sheetBinding.cardFoto3.context)
                    .load(R.drawable.white_image)
                    .error(R.drawable.white_image)
                    .into(sheetBinding.cardFoto3)
            }
            sheetBinding.isNama.text = it.user.name
            sheetBinding.isEmail.text = it.user.email
            sheetBinding.isNamaPenerima.text = it.shipping_address.name
            sheetBinding.isAlamat.text = it.shipping_address.address
            sheetBinding.isDetailAlamat.text = it.shipping_address.detail_address
            sheetBinding.isKurir.text = it.courier + " " + it.courier_package
            setContentView(sheetBinding.root)
            show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sheetBinding = BottomSheetAdminBinding.inflate(layoutInflater)
        sheetDialog = BottomSheetDialog(this)

        sheetBinding.btnClose.setOnClickListener {
            sheetDialog.dismiss()
        }

        binding.verifyAcc.setOnClickListener {
            val i = Intent(this, SuperActivity::class.java)
            startActivity(i)
            finish()
        }
        lifecycleScope.launch {
            viewModel.listComplete()
        }
        setupObserver()
    }

    override fun setupObserver() {
        val loading = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) loading.show() else loading.dismiss()
        }
        viewModel.complete.observe(this) {
            Log.e("TAG", "setupObserver: " + it)
            theAdapter.complete.clear()
            theAdapter.complete.addAll(it.data)
            binding.rvCategory.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = CompleteAdapter(it.data.toMutableList())
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this, SuperActivity::class.java)
        startActivity(i)
        finish()
    }
}