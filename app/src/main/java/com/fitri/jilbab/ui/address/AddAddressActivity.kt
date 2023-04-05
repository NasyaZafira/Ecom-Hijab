package com.fitri.jilbab.ui.address

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.R
import com.fitri.jilbab.databinding.ActivityAddAddressBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class AddAddressActivity : BaseActivity() {

    private lateinit var binding: ActivityAddAddressBinding
    private val viewModel: AddressViewModel by viewModels()
    private val listSpinner: MutableList<String> = ArrayList()
    private val listId: MutableList<Int> = ArrayList()
    private var idValue: String = " "


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.provList()
        }

        binding.verifyAcc.setOnClickListener {
            val i = Intent(this, ListAddressActivity::class.java)
            startActivity(i)
            finish()
        }

        btnSave()
        province()
        f_listSpinner()
    }

    private fun province() {
        lifecycleScope.launch{
            viewModel.provList()
        }
    }

    private fun btnSave() {
        binding.btnContinue.setOnClickListener {
            lifecycleScope.launch {
                val name = binding.isedtName.text.toString().trim()
                val number = binding.isedtNumber.text.toString().trim()
                val address = binding.iesdtAddress.text.toString().trim()
                val detail = binding.isedtDetail.text.toString().trim()
                val utama = binding.cbPolicy.isChecked
                //val prov =
                //val cities =
                viewModel.add(address, "", detail, utama, name, number, "")
            }
        }
    }

    override fun setupObserver() {
        loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) showLoading() else hideLoading()
        }
        viewModel.lisProvince.observe(this) {
            for (i: Int in 0 until it.data.hashCode()) {
                listSpinner.add(it.data.hashCode().toString())
                listId.add(it.data.hashCode())
            }
        }
        viewModel.addAddress.observe(this) {
            Toast.makeText(this, "Berhasil Menambahkan Alamat", Toast.LENGTH_LONG).show()
            val i = Intent(this, ListAddressActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun f_listSpinner() {

        val arrayAdapterKategori = ArrayAdapter(this, R.layout.signup_menu, listSpinner)
        val autoCompleteKategori = binding.auProv
        autoCompleteKategori.setAdapter(arrayAdapterKategori)

        binding.auProv.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            idValue = listId.get(position).toString()
            Log.e("TAG", "f_listSpinner: " + idValue)
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}