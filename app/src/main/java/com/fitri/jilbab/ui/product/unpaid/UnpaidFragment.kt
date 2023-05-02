package com.fitri.jilbab.ui.product.unpaid

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.MainActivity
import com.fitri.jilbab.data.model.transaction.unpaid.Data
import com.fitri.jilbab.databinding.FragmentUnpaidBinding
import com.fitri.jilbab.ui.product.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UnpaidFragment : Fragment() {
    private var _binding: FragmentUnpaidBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OrderViewModel by viewModels()
    private var unpadapter =
        UnpaidAdapter(
            mutableListOf(),
            onCancle = { data, position -> intentToHoax(data, position) })


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

    private fun intentToHoax(content: Data, position: Int) {
//        Log.e("TAG", "intentToHoax: " + content.url
        lifecycleScope.launch {
            viewModel.postCancle(content.id_order)
        }

    }

    private fun setupObserver() {

        val loading = CustomLoadingDialog(requireContext())
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) loading.show() else loading.dismiss()
        }

        viewModel.postcancle.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "Berhasil Membatalkan Pesanan", Toast.LENGTH_LONG)
                .show()
            val i = Intent(requireContext(), MainActivity::class.java)
            startActivity(i)
            requireActivity().finish()
        }

        viewModel.unpaid.observe(viewLifecycleOwner) {
            unpadapter.unpaid.clear()
            unpadapter.unpaid.addAll(it.data)
            binding.rvCategory.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                adapter = unpadapter
            }

            //Log.e("TAG", "setupObserver: --------------------------------------" )
            //Log.e("TAG", "setupObserver: " + it.data.toMutableList())

        }

    }

}