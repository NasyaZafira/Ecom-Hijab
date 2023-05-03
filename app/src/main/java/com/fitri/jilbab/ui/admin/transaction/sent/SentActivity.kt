package com.fitri.jilbab.ui.admin.transaction.sent

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.MainActivity
import com.fitri.jilbab.data.model.transaction.sent.Data
import com.fitri.jilbab.databinding.ActivitySentBinding
import com.fitri.jilbab.ui.admin.SuperActivity
import com.fitri.jilbab.ui.product.OrderViewModel
import com.fitri.jilbab.ui.product.sent.SentAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SentActivity : BaseActivity() {

    private lateinit var binding: ActivitySentBinding
    private val viewModel: OrderViewModel by viewModels()
    private var adSentAdapt =
        SentAdapter(mutableListOf(), onDone = { data, position -> intentToDone(data, position) })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.verifyAcc.setOnClickListener {
            val i = Intent(this, SuperActivity::class.java)
            startActivity(i)
            finish()
        }
        lifecycleScope.launch {
            viewModel.lisSent()
        }
        setupObserver()
    }

    private fun intentToDone(content: Data, position: Int) {
        lifecycleScope.launch {
            viewModel.postDone(content.id_order, "complete")
        }
    }

    override fun setupObserver() {
        val loading = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) loading.show() else loading.dismiss()
        }
        viewModel.postDone.observe(this) {
            Toast.makeText(this, "Pesanan Berhasil Diterima", Toast.LENGTH_LONG)
                .show()
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
        viewModel.sent.observe(this) {
            adSentAdapt.sent.clear()
            adSentAdapt.sent.addAll(it.data)
            binding.rvCategory.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                adapter = adSentAdapt
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