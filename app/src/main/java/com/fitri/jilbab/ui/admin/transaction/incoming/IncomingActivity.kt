package com.fitri.jilbab.ui.admin.transaction.incoming

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.data.model.transaction.incoming.Data
import com.fitri.jilbab.databinding.ActivityIncomingBinding
import com.fitri.jilbab.ui.admin.SuperActivity
import com.fitri.jilbab.ui.product.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IncomingActivity : BaseActivity() {

    private lateinit var binding: ActivityIncomingBinding
    private val viewModel: OrderViewModel by viewModels()
    private var incAdmin = AincomAdapter(mutableListOf(), onCancle = { data, position ->
        intentToHoax(data, position)
    })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncomingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.verifyAcc.setOnClickListener {
            val i = Intent(this, SuperActivity::class.java)
            startActivity(i)
            finish()
        }
        lifecycleScope.launch {
            viewModel.listIncom()
        }
        setupObserver()
    }

    private fun intentToHoax(content: Data, position: Int) {
        lifecycleScope.launch {
            viewModel.postPacked(content.id_order, "packed")
        }

    }

    override fun setupObserver() {
        val loading = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) loading.show() else loading.dismiss()
        }
        viewModel.postPack.observe(this) {
            Toast.makeText(this, "Berhasil memperbarui status pemesanan", Toast.LENGTH_LONG)
                .show()
            val i = Intent(this, SuperActivity::class.java)
            startActivity(i)
            finish()
        }
        viewModel.incom.observe(this) {
            incAdmin.pack.clear()
            incAdmin.pack.addAll(it.data)
            binding.rvCategory.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                adapter = incAdmin
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