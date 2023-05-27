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
import com.fitri.jilbab.data.model.user.cat.Data
import com.fitri.jilbab.databinding.ActivityDetailCategoryBinding
import com.fitri.jilbab.ui.home.DetailProductActivity
import com.fitri.jilbab.ui.search.SearchActivity
import com.fitri.jilbab.ui.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailCategory : BaseActivity() {

    private lateinit var binding: ActivityDetailCategoryBinding
    private val viewModel: SearchViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.verifyAcc.setOnClickListener {
            val i = Intent(this, ListCatActivity::class.java)
            startActivity(i)
            finish()
        }
        f_extras()
        setupObserver()
    }


    private fun f_extras() {
        val id = intent.getLongExtra("product", 0)
        f_launch(id.toInt())
    }

    private fun f_launch(category: Int) {
        lifecycleScope.launch {
//            Log.e("TAG", "onCreate: id product " + data.id_product )
            viewModel.listProductCat(category.toString())
            setupObserver()
        }
    }

    private fun intentToDetail(content: Data) {
        val i = Intent(this, DetailProductActivity::class.java)
        i.putExtra("product", content.id_product!!.toLong())
        startActivity(i)
    }

    override fun setupObserver() {
        val loading = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) loading.show() else loading.dismiss()
        }
        viewModel.cat.observe(this) { response ->
            binding.rvProduct.apply {
                layoutManager = LinearLayoutManager(
                    context, RecyclerView.VERTICAL,
                    true
                )
                adapter = UsrCatAdapter(response.data.toMutableList(),
                    onDetailCLick = { data ->
                        intentToDetail(data)
                    })
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}