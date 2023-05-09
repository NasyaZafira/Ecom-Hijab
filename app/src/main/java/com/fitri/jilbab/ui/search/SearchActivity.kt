package com.fitri.jilbab.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.MainActivity
import com.fitri.jilbab.R
import com.fitri.jilbab.data.local.SharedPref
import com.fitri.jilbab.data.model.user.search.Data
import com.fitri.jilbab.databinding.ActivitySearchBinding
import com.fitri.jilbab.ui.admin.SuperActivity
import com.fitri.jilbab.ui.home.DetailProductActivity
import com.fitri.jilbab.ui.search.categoryUsr.ListCatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(R.style.Theme_FitriJilbab_Home)

        binding.editSearch.setOnEditorActionListener { textView, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                lifecycleScope.launch {
                    viewModel.searchProduct(textView.text.toString().trim())
                }
            }
            true
        }
        binding.layoutCat.setOnClickListener {
            val i = Intent(this, ListCatActivity::class.java)
            startActivity(i)
            finish()

        }
        binding.verifyAcc.setOnClickListener {
            if (SharedPref.isLoggedIn) {
                if (SharedPref.userRole == "customer") {
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    finish()
                }
            } else {
                val intent = Intent(this, SuperActivity::class.java)
                startActivity(intent)
            }
        }
        setupObserver()
    }


    override fun setupObserver() {
        val loading = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) loading.show() else loading.dismiss()
        }

        viewModel.statusCode.observe(this) {
            if (it == 200) {
                binding.rvProduct.visibility = View.VISIBLE
            } else {
                binding.rvProduct.visibility = View.INVISIBLE
            }
        }

        viewModel.search.observe(this) { response ->

            Log.e("TAG", "setupObserver: " + response)
            if (response.data.isNotEmpty()) {
                binding.rvProduct.visibility = View.VISIBLE
            } else {
                binding.txtNoResult.visibility = View.VISIBLE
                binding.rvProduct.visibility = View.GONE
            }
            binding.rvProduct.apply {
                layoutManager = LinearLayoutManager(
                    context, RecyclerView.VERTICAL,
                    true
                )
                adapter = SearchAdapter(
                    response.data.toMutableList(),
                    onDetailCLick = { data ->
                        intentToDetail(data)
                    })
            }
        }
    }

    private fun intentToDetail(content: Data) {
        val i = Intent(this, DetailProductActivity::class.java)
        i.putExtra("product", content.id_product!!.toLong())
        startActivity(i)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}