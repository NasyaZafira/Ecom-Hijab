package com.fitri.jilbab.ui.admin.transaction.packed

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.transaction.packed.Data
import com.fitri.jilbab.databinding.ActivityPackedBinding
import com.fitri.jilbab.databinding.DialogSentBinding
import com.fitri.jilbab.ui.admin.SuperActivity
import com.fitri.jilbab.ui.product.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PackedActivity : BaseActivity() {

    private lateinit var binding: ActivityPackedBinding
    private val viewModel: OrderViewModel by viewModels()
    private var adpackAdapt =
        AdpackedAdapter(mutableListOf(), onCancle = { data, position -> intentTo(data, position) })
    private lateinit var dialogSent: DialogSentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPackedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(R.style.Theme_FitriJilbab_Home)

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
        viewModel.postSent.observe(this){
            Toast.makeText(this, "Berhasil memperbarui status pemesanan", Toast.LENGTH_LONG)
                .show()
            val i = Intent(this, SuperActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this, SuperActivity::class.java)
        startActivity(i)
        finish()
    }
}