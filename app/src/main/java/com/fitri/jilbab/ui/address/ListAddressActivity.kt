package com.fitri.jilbab.ui.address

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.databinding.ActivityListAddressBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListAddressActivity : BaseActivity() {
    private lateinit var binding: ActivityListAddressBinding
    private val viewModel : AddressViewModel by viewModels()
    private var adAdapter = AddressAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch{
            viewModel.isList()
        }
        setupObserver()
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
}