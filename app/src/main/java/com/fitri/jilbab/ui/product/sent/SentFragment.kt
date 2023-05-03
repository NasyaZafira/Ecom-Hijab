package com.fitri.jilbab.ui.product.sent

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
import com.fitri.jilbab.data.model.transaction.sent.Data
import com.fitri.jilbab.databinding.FragmentSentBinding
import com.fitri.jilbab.ui.product.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SentFragment : Fragment() {
    private var _binding: FragmentSentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OrderViewModel by viewModels()
    private var sentAdapt = SentAdapter(mutableListOf(), onDone = { data, position -> intentToDone(data, position) })


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSentBinding.bind(view)

        lifecycleScope.launch {
            viewModel.lisSent()
        }
        setupObserver()
    }
    private fun intentToDone(content: Data, position: Int){
        lifecycleScope.launch {
            viewModel.postDone(content.id_order, "complete")
        }

    }

    private fun setupObserver() {
        val loading = CustomLoadingDialog(requireContext())
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) loading.show() else loading.dismiss()
        }
        viewModel.postDone.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), "Pesanan Berhasil Diterima", Toast.LENGTH_LONG)
                .show()
            val i = Intent(requireContext(), MainActivity::class.java)
            startActivity(i)
            requireActivity().finish()
        }
        viewModel.sent.observe(viewLifecycleOwner){
            sentAdapt.sent.clear()
            sentAdapt.sent.addAll(it.data)
            binding.rvCategory.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                adapter = sentAdapt
            }
        }
    }


}