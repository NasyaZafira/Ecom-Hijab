package com.fitri.jilbab.ui.product.unpaid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.databinding.FragmentUnpaidBinding
import com.fitri.jilbab.ui.product.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UnpaidFragment            : Fragment() {
    private var _binding        : FragmentUnpaidBinding? = null
    private val binding get()   = _binding!!
    private val viewModel       : OrderViewModel by viewModels()
    private var unpadapter      = UnpaidAdapter(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUnpaidBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUnpaidBinding.bind(view)

        lifecycleScope.launch {
            viewModel.listUnpaid()
        }
        setupObserver()
    }

    private fun setupObserver() {

        val loading = CustomLoadingDialog(requireContext())
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) loading.show() else loading.dismiss()
        }

        viewModel.unpaid.observe(viewLifecycleOwner){
            unpadapter.unpaid.clear()
            unpadapter.unpaid.addAll(it.data)
            binding.rvCategory.apply {
                layoutManager   = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                adapter         = UnpaidAdapter(it.data.toMutableList())
            }

            //Log.e("TAG", "setupObserver: --------------------------------------" )
            //Log.e("TAG", "setupObserver: " + it.data.toMutableList())

        }

    }

}