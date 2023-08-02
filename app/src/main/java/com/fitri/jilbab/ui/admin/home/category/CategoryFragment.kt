package com.fitri.jilbab.ui.admin.home.category

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
import com.fitri.jilbab.data.model.admin.category.Data
import com.fitri.jilbab.databinding.FragmentCategoryBinding
import com.fitri.jilbab.ui.admin.product_admin.ProductAdminVm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment() {
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductAdminVm by viewModels()
    private var targetPosition = -1
    private var adapterCg = CategoryAdapter(mutableListOf(), onDetailClick = {data, pos -> intentToDetail(data, pos)})



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCategoryBinding.bind(view)

        lifecycleScope.launch{
            viewModel.categoryList()
        }
        binding.btnCategory.setOnClickListener {
            val i = Intent(requireActivity(), AddCategoryActivity::class.java)
            startActivity(i)
            requireActivity().finish()
        }
        setupObserver()
    }

    private fun intentToDetail(content: Data, pos: Int) {
        targetPosition = pos
        val i = Intent(requireContext(), EditCategoryActivity::class.java)
        i.putExtra("category", content)
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
        viewModel.category.observe(viewLifecycleOwner){
            adapterCg.cgAdmin.clear()
            adapterCg.cgAdmin.addAll(it.data)
            adapterCg.notifyDataSetChanged()
            binding.rvCategory.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL , true)
                adapter = adapterCg
            }
        }
    }
}