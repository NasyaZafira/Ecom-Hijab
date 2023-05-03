package com.fitri.jilbab.ui.admin.transaction.complete

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.R
import com.fitri.jilbab.databinding.ActivityCompleteBinding
import com.fitri.jilbab.databinding.ActivitySentBinding
import com.fitri.jilbab.ui.admin.SuperActivity
import com.fitri.jilbab.ui.product.OrderViewModel
import com.fitri.jilbab.ui.product.complete.CompleteAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class CompleteActivity : BaseActivity() {

    private lateinit var binding: ActivityCompleteBinding
    private val viewModel: OrderViewModel by viewModels()
    private var theAdapter = CompleteAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            Log.e("TAG", "setupObserver: "+ it )
            theAdapter.complete.clear()
            theAdapter.complete.addAll(it.data)
            binding.rvCategory.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
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