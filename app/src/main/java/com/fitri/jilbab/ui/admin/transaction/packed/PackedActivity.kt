package com.fitri.jilbab.ui.admin.transaction.packed

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.transaction.packed.newPacked.Data
import com.fitri.jilbab.databinding.ActivityPackedBinding
import com.fitri.jilbab.databinding.BottomSheetAdminBinding
import com.fitri.jilbab.databinding.DialogSentBinding
import com.fitri.jilbab.ui.admin.SuperActivity
import com.fitri.jilbab.ui.product.OrderViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PackedActivity : BaseActivity() {

    private lateinit var binding: ActivityPackedBinding
    private val viewModel: OrderViewModel by viewModels()
    private lateinit var dialogSent: DialogSentBinding
    private lateinit var sheetDialog: BottomSheetDialog
    private lateinit var sheetBinding: BottomSheetAdminBinding

    @SuppressLint("SetTextI18n")
    private var adpackAdapt =
        AdpackedAdapter(mutableListOf(), onCancle = { data, position -> intentTo(data, position) }) {
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
        binding = ActivityPackedBinding.inflate(layoutInflater)
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
            viewModel.listPacked()
        }
        setupObserver()

    }

    private fun intentTo(content: Data, position: Int) {
        dialogSent = DialogSentBinding.inflate(layoutInflater)
        val dialog = Dialog(this@PackedActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(dialogSent.root)
        dialog.show()

        dialogSent.btnKonfir.setOnClickListener {
            val resi = dialogSent.isTresi.text.toString().trim()
            if (!resi.isNullOrBlank()) {
                lifecycleScope.launch {
                    viewModel.postSent(content.id_order, resi, "sent")
                }
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Lengkapi Nomor Resi", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun setupObserver() {
        val loading = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) loading.show() else loading.dismiss()
        }
        viewModel.packed.observe(this) {
            adpackAdapt.pack.clear()
            adpackAdapt.pack.addAll(it.data)
            binding.rvCategory.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                adapter = adpackAdapt
            }
        }
        viewModel.postSent.observe(this) {
            Toast.makeText(this, "Berhasil memperbarui status pemesanan", Toast.LENGTH_LONG)
                .show()
            lifecycleScope.launch {
                viewModel.listPacked()
            }
//            val i = Intent(this, SuperActivity::class.java)
//            startActivity(i)
//            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this, SuperActivity::class.java)
        startActivity(i)
        finish()
    }
}