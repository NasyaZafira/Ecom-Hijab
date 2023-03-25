package com.fitri.jilbab.ui.admin.product_admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.R
import com.fitri.jilbab.databinding.ActivityAddProductBinding
import com.fitri.jilbab.databinding.ActivitySigninBinding
import com.fitri.jilbab.ui.admin.SuperActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class AddProductActivity : BaseActivity() {

    private lateinit var    binding             : ActivityAddProductBinding
    private val             viewModel           : ProductAdminVm by viewModels()
    private var             selectedFiles       = mutableListOf<File>()

    private val             File.size get()     = if (!exists()) 0.0 else length().toDouble()
    private val             File.sizeInKb get() = size      / 1024
    private val             File.sizeInMb get() = sizeInKb  / 1024

    private val             listSpinner         : MutableList<String>   = ArrayList()
    private val             listId              : MutableList<Int>      = ArrayList()
    private var             idValue             : String                = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        f_back()
        f_continue()
        f_runCategory()
        f_listSpinner()

    }

    private fun f_runCategory() {
        lifecycleScope.launch {
            viewModel.categoryList()
        }
        setupObserver()
    }

    override fun setupObserver() {
        viewModel.category.observe(this){
            val result = it.data.toMutableList()
            for (i : Int in 0 until result.size){
                listSpinner.add(it.data[i].category_name)
                listId.add(it.data[i].id_category)
            }
            //listSpinner.add(it.data.toMutableList().toString())
        }
    }

    private fun f_listSpinner() {
        val gender = resources.getStringArray(R.array.gender)

        val arrayAdapterKategori = ArrayAdapter(this, R.layout.signup_menu, listSpinner)
        val autoCompleteKategori = binding.autoCompleteTxtKategori
        autoCompleteKategori.setAdapter(arrayAdapterKategori)

        binding.autoCompleteTxtKategori.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            idValue = listId.get(position).toString()
            Log.e("TAG", "f_listSpinner: " + idValue)
        })
    }

    private fun f_back() {
        binding.verifyAcc.setOnClickListener {
            val i = Intent(this, SuperActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun f_continue() {
        binding.btnContinue.setOnClickListener {
            lifecycleScope.launch {
                val name        = binding.edtNmProduct.text.toString().trim()
                val harga       = binding.editPrice.text.toString().trim()
                val diskon      = binding.edtDiskon.text.toString().trim()
                val category    = idValue //binding.autoCompleteTxtKategori.text.toString().trim()
            }
        }
    }



}