package com.fitri.jilbab.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fitri.jilbab.databinding.FragmentProductAdminBinding
import com.fitri.jilbab.ui.admin.product_admin.PaAdapter
import com.fitri.jilbab.ui.admin.product_admin.ProductAdminVm
import kotlinx.coroutines.launch


class ProductAdmin : Fragment() {


    private var _binding: FragmentProductAdminBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductAdminVm by viewModels()
    private var adapterAdm = PaAdapter(mutableListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductAdminBinding.bind(view)

        lifecycleScope.launch {
            viewModel.theList()
        }

        viewModel.list.observe(viewLifecycleOwner){
            adapterAdm.prAdmin.clear()
            adapterAdm.prAdmin.addAll(it.data)
            adapterAdm.notifyDataSetChanged()
            binding.rvProductadm.apply {
                layoutManager = GridLayoutManager(context,  2)
                adapter = adapterAdm
            }
        }

    }

}
