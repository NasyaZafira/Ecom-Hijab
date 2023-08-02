package com.fitri.jilbab.ui.address

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fitri.jilbab.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.data.model.address.Data

import com.fitri.jilbab.databinding.ActivityListAddressBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListAddressActivity : BaseActivity() {
    private lateinit var binding: ActivityListAddressBinding
    private val viewModel : AddressViewModel by viewModels()
    private var adAdapter = AddressAdapter(mutableListOf(), onDetail = {data, pos -> intentToDetail(data, pos)})
    private var targetPosition = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddress.setOnClickListener {
            val i = Intent(this, AddAddressActivity::class.java)
            startActivity(i)
        }
        lifecycleScope.launch{
            viewModel.isList()
        }
        setupObserver()

        binding.verifyAcc.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                viewModel.isList()
            }
            binding.swipeRefresh.isRefreshing = false
        }
    }
    private fun intentToDetail(content: Data, pos: Int) {
        targetPosition = pos
        val i = Intent(this, EditAddressActivity::class.java)
        i.putExtra("address", content)
        startActivity(i)
    }
    override fun setupObserver() {
        loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) showLoading() else hideLoading()
        }

        viewModel.listAdress.observe(this){
            adAdapter.address.clear()
            adAdapter.address.addAll(it.data)
            adAdapter.notifyDataSetChanged()
            binding.rcAddress.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                adapter = adAdapter
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}