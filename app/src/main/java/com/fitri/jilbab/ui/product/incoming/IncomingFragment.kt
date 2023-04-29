package com.fitri.jilbab.ui.product.incoming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.R
import com.fitri.jilbab.databinding.FragmentIncomingBinding
import com.fitri.jilbab.databinding.FragmentTransactionBinding
import com.fitri.jilbab.databinding.FragmentUnpaidBinding
import com.fitri.jilbab.ui.product.OrderViewModel
import com.fitri.jilbab.ui.product.unpaid.UnpaidAdapter
import kotlinx.coroutines.launch


class IncomingFragment : Fragment() {
    private var _binding: FragmentIncomingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OrderViewModel by viewModels()
    private var incadapter = Incomingadapter(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIncomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentIncomingBinding.bind(view)

        lifecycleScope.launch {
            viewModel.listIncom()
        }
        setupObserver()
    }

    private fun setupObserver() {
        val loading = CustomLoadingDialog(requireContext())
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) loading.show() else loading.dismiss()
        }
        viewModel.incom.observe(viewLifecycleOwner){
            incadapter.incom.clear()
            incadapter.incom.addAll(it.data)
            binding.rvCategory.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                adapter = Incomingadapter(it.data.toMutableList())
            }
        }
    }
}