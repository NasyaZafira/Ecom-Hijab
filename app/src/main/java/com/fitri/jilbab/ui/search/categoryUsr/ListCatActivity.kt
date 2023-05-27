package com.fitri.jilbab.ui.search.categoryUsr

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.admin.category.Data
import com.fitri.jilbab.databinding.ActivityListCatBinding
import com.fitri.jilbab.ui.admin.product_admin.ProductAdminVm
import com.fitri.jilbab.ui.home.DetailProductActivity
import com.fitri.jilbab.ui.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListCatActivity : BaseActivity() {

    private lateinit var binding: ActivityListCatBinding
    private val viewModel: ProductAdminVm by viewModels()
    private var adapterCg = CatAdapter(mutableListOf(), onDetailCLick = {data -> intentToDetail(data)})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.verifyAcc.setOnClickListener {
            val i = Intent(this, SearchActivity::class.java)
            startActivity(i)
            finish()
        }
        lifecycleScope.launch{
            viewModel.categoryList()
        }
    setupObserver()
    }


    private fun intentToDetail(content: Data ) {
        val i = Intent(this, DetailCategory::class.java)
        i.putExtra("product", content.id_category!!.toLong())
        startActivity(i)
        finish()
    }

    override fun setupObserver() {
        val loading = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) loading.show() else loading.dismiss()
        }
        viewModel.category.observe(this){
            adapterCg.cgUser.clear()
            adapterCg.cgUser.addAll(it.data)
            adapterCg.notifyDataSetChanged()
            binding.rvCat.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL , true)
                adapter = adapterCg
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
