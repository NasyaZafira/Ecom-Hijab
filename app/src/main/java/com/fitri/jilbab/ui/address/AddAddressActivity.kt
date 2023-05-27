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

    private lateinit var    binding     : ActivityAddAddressBinding
    private val             viewModel   : AddressViewModel by viewModels()

    private val             listSpinner : MutableList<String>   = ArrayList()
    private val             listId      : MutableList<Int>      = ArrayList()
    private var             idValue     : String                = " "

    private val             listSpinnerCity : MutableList<String>   = ArrayList()
    private val             listIdCity      : MutableList<Int>      = ArrayList()
    private var             idValueCity     : String                = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        f_verifAcc()
        f_btnSave()
        f_province()
        f_listSpinner()
        f_listSpinnerCity()
    }

    private fun f_verifAcc(){
        binding.verifyAcc.setOnClickListener {
            val i = Intent(this, ListAddressActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun f_province() {
        lifecycleScope.launch{
            viewModel.provList()
        }
        setupObserver()
    }

    private fun f_city(id : String) {
        lifecycleScope.launch{
            viewModel.citiesList(id.toInt())
        }
        setupObserver()
    }

    private fun f_btnSave() {
        binding.btnContinue.setOnClickListener {
            lifecycleScope.launch {
                val name        = binding.isedtName.text.toString().trim()
                val number      = binding.isedtNumber.text.toString().trim()
                val address     = binding.iesdtAddress.text.toString().trim()
                val detail      = binding.isedtDetail.text.toString().trim()
                val utama       = binding.cbPolicy.isChecked
                val prov        = idValue
                val cities      = idValueCity
                viewModel.add(address, cities, detail, utama, name, number, prov)
            }
        }
    }

    override fun setupObserver() {
        loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) showLoading() else hideLoading()
        }
        viewModel.lisProvince.observe(this) {
            Log.e("TAG", "setupObserver: " + it )
            listSpinner.add(it.data.prov1)
            listSpinner.add(it.data.prov2)
            listSpinner.add(it.data.prov3)
            listSpinner.add(it.data.prov4)
            listSpinner.add(it.data.prov5)
            listSpinner.add(it.data.prov6)
            listSpinner.add(it.data.prov7)
            listSpinner.add(it.data.prov8)
            listSpinner.add(it.data.prov9)
            listSpinner.add(it.data.prov10)
            listSpinner.add(it.data.prov11)
            listSpinner.add(it.data.prov12)
            listSpinner.add(it.data.prov13)
            listSpinner.add(it.data.prov14)
            listSpinner.add(it.data.prov15)
            listSpinner.add(it.data.prov16)
            listSpinner.add(it.data.prov17)
            listSpinner.add(it.data.prov18)
            listSpinner.add(it.data.prov19)
            listSpinner.add(it.data.prov20)
            listSpinner.add(it.data.prov21)
            listSpinner.add(it.data.prov22)
            listSpinner.add(it.data.prov23)
            listSpinner.add(it.data.prov24)
            listSpinner.add(it.data.prov25)
            listSpinner.add(it.data.prov26)
            listSpinner.add(it.data.prov27)
            listSpinner.add(it.data.prov28)
            listSpinner.add(it.data.prov29)
            listSpinner.add(it.data.prov30)
            listSpinner.add(it.data.prov31)
            listSpinner.add(it.data.prov32)
            listSpinner.add(it.data.prov33)
            listSpinner.add(it.data.prov34)
            for (i : Int in 1 until 35){
                Log.e("TAG", "setupObserver: " + i )
                listId.add(i)
            }
        }
        viewModel.addAddress.observe(this) {
            Toast.makeText(this, "Berhasil Menambahkan Alamat", Toast.LENGTH_LONG).show()
            val i = Intent(this, ListAddressActivity::class.java)
            startActivity(i)
            finish()
        }
        viewModel.listCity.observe(this){
            Log.e("TAG", "setupObserver: " + it )
            val result = it.data.toMutableList()
            listSpinnerCity.clear()
            listIdCity.clear()
            for (i: Int in 0 until result.size) {
                listSpinnerCity.add(it.data[i].name)
                listIdCity.add(it.data[i].city_id)
            }
        }
    }

    private fun f_listSpinner() {
        val arrayAdapterKategori = ArrayAdapter(this, R.layout.signup_menu, listSpinner)
        val autoCompleteKategori = binding.auProv
        autoCompleteKategori.setAdapter(arrayAdapterKategori)

        binding.auProv.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            idValue = listId.get(position).toString()
            Log.e("TAG", "f_listSpinner: " + idValue)
            f_city(idValue)
        })
    }

    private fun f_listSpinnerCity() {
        val arrayAdapterCityKategori = ArrayAdapter(this, R.layout.signup_menu, listSpinnerCity)
        val autoCompleteCityKategori = binding.auCity
        autoCompleteCityKategori.setAdapter(arrayAdapterCityKategori)

        binding.auCity.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            idValueCity = listIdCity.get(position).toString()
            Log.e("TAG", "f_listSpinner: " + idValueCity)
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}