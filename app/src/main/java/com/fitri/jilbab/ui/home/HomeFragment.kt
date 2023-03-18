package com.fitri.jilbab.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.data.local.SharedPref
import com.fitri.jilbab.databinding.FragmentHomeBinding
import com.fitri.jilbab.ui.admin.product_admin.ProductAdminVm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(){
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductAdminVm by viewModels()
    private var adapterUsr = PuAdapter(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        lifecycleScope.launch {
            viewModel.theList()
        }

        binding.txtTittle.text = "Halo, " + SharedPref.nameUser
        setupObserver()

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
            adapterUsr.prUser.clear()
            adapterUsr.prUser.addAll(it.data)
            adapterUsr.notifyDataSetChanged()
            binding.rvProductuser.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = adapterUsr
            }
        }
        viewModel.list.observe(viewLifecycleOwner){
            adapterUsr.prUser.clear()
            adapterUsr.prUser.addAll(it.data)
            adapterUsr.notifyDataSetChanged()
            binding.rvListNew.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = adapterUsr
            }
        }
    }

}