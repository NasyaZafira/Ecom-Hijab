package com.fitri.jilbab.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.data.local.SharedPref
import com.fitri.jilbab.data.model.admin.product.listNew.Data
import com.fitri.jilbab.databinding.FragmentHomeBinding
import com.fitri.jilbab.ui.admin.product_admin.ProductAdminVm
import com.fitri.jilbab.ui.cart.CartActivity
import com.fitri.jilbab.ui.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding        : FragmentHomeBinding? = null
    private val binding get()   = _binding!!
    private val viewModel       : ProductAdminVm by viewModels()
    private var adapterUsr      = PuAdapter(mutableListOf(), onDetailCLick = { data -> intentToDetail(data) })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        lifecycleScope.launch {
            viewModel.theList()
        }

        binding.btnCart.setOnClickListener {
            val i = Intent(requireContext(), CartActivity::class.java)
            startActivity(i)
            requireActivity().finish()
        }

        binding.editSearch.setOnClickListener {
            val i = Intent(requireContext(), SearchActivity::class.java)
            startActivity(i)
            requireActivity().finish()
        }

        SharedPref.idNav = 1
        setupObserver()
    }

    private fun intentToDetail(content: Data) {
        val i = Intent(requireContext(), DetailProductActivity::class.java)
        i.putExtra("product", content.id_product!!.toLong())
        startActivity(i)
    }

    private fun setupObserver() {
        val loading = CustomLoadingDialog(requireContext())

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) loading.show() else loading.dismiss()
        }

        viewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

        viewModel.list.observe(viewLifecycleOwner) {
            Log.e("TAG", "setupObserver: " + it)
            adapterUsr.prUser.clear()
            adapterUsr.prUser.addAll(it.data)
            adapterUsr.notifyDataSetChanged()
            binding.rvProductuser.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = adapterUsr
            }
        }
    }



}


















