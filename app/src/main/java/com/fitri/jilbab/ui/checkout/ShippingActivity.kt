package com.fitri.jilbab.ui.checkout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.user.checkout.Courier
import com.fitri.jilbab.databinding.ActivityShippingBinding
import com.fitri.jilbab.ui.cart.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShippingActivity : BaseActivity() {

    private lateinit var binding: ActivityShippingBinding
    private lateinit var radioGroup: RadioGroup
    private var kurir: String = ""
    private var service: String = ""
    private var price: String = ""
    private var estimate: String = ""
    private lateinit var courier: Courier
    private lateinit var radioTiki: RadioGroup
    private lateinit var radioPos : RadioGroup
    private val viewModel: CartViewModel by viewModels()

    private var text11 = ""
    private var text12 = ""
    private var text13 = ""
    private var text21 = ""
    private var text22 = ""
    private var text31 = ""
    private var text32 = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShippingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(R.style.Theme_FitriJilbab_Home)

        r_intent()
        r_initiation()
        r_radioGroup()
        r_button()
        setupObserver()


    }

    override fun setupObserver() {
        loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) showLoading() else hideLoading()
        }

        Glide.with(binding.ivPoster.context)
            .load("https://ecom-mobile.spdev.my.id/assets/img/jne.png")
            .error(R.drawable.white_image)
            .into(binding.ivPoster)
        Glide.with(binding.ivPoster2.context)
            .load("https://ecom-mobile.spdev.my.id/assets/img/tiki.png")
            .error(R.drawable.white_image)
            .into(binding.ivPoster2)
        Glide.with(binding.ivPoster3.context)
            .load("https://ecom-mobile.spdev.my.id/assets/img/pos.png")
            .error(R.drawable.white_image)
            .into(binding.ivPoster3)
    }

    private fun r_intent() {
        intent.extras!!.getParcelable<Courier>("shipping").let {
            Log.e("TAG", "r_intent: " + it!!)
            courier = it

            binding.txtName.text    = it.jne[0].code
            binding.txtName2.text   = it.tiki[0].code
            binding.txtName3.text   = it.pos[0].code

            binding.one.text    = it.jne[0].costs[0].service + "\nOngkos Kirim RP."+ it.jne[0].costs[0].cost[0].value + "\nEstimasi kedatangan " + it.jne[0].costs[0].cost[0].etd + " hari"
            binding.two.text    = it.jne[0].costs[1].service + "\nOngkos Kirim RP."+ it.jne[0].costs[1].cost[0].value + "\nEstimasi kedatangan " + it.jne[0].costs[1].cost[0].etd + " hari"
            binding.three.text  = it.jne[0].costs[2].service + "\nOngkos Kirim RP."+ it.jne[0].costs[2].cost[0].value + "\nEstimasi kedatangan " + it.jne[0].costs[2].cost[0].etd + " hari"

            text11              = it.jne[0].costs[0].service + "\nOngkos Kirim RP."+ it.jne[0].costs[0].cost[0].value + "\nEstimasi kedatangan " + it.jne[0].costs[0].cost[0].etd + " hari" //it.jne[0].code + it.jne[0].costs[0].service + it.jne[0].costs[0].cost[0].value
            text12              = it.jne[0].costs[1].service + "\nOngkos Kirim RP."+ it.jne[0].costs[1].cost[0].value + "\nEstimasi kedatangan " + it.jne[0].costs[1].cost[0].etd + " hari"
            text13              = it.jne[0].costs[2].service + "\nOngkos Kirim RP."+ it.jne[0].costs[2].cost[0].value + "\nEstimasi kedatangan " + it.jne[0].costs[2].cost[0].etd + " hari"

            binding.one2.text   = it.tiki[0].costs[0].service + "\nOngkos Kirim RP."+ it.tiki[0].costs[0].cost[0].value + "\nEstimasi kedatangan " + it.jne[0].costs[0].cost[0].etd + " hari"
            binding.two2.text   = it.tiki[0].costs[1].service + "\nOngkos Kirim RP."+ it.tiki[0].costs[1].cost[0].value + "\nEstimasi kedatangan " + it.jne[0].costs[1].cost[0].etd + " hari"

            text21              = it.tiki[0].costs[0].service + "\nOngkos Kirim RP."+ it.tiki[0].costs[0].cost[0].value + "\nEstimasi kedatangan " + it.jne[0].costs[0].cost[0].etd + " hari"
            text22              = it.tiki[0].costs[1].service + "\nOngkos Kirim RP."+ it.tiki[0].costs[1].cost[0].value + "\nEstimasi kedatangan " + it.jne[0].costs[1].cost[0].etd + " hari"

            binding.one3.text   = it.pos[0].costs[0].service + "\nOngkos Kirim RP."+ it.pos[0].costs[0].cost[0].value + "\nEstimasi kedatangan " + it.jne[0].costs[0].cost[0].etd + " hari"
            binding.two3.text   = it.pos[0].costs[1].service + "\nOngkos Kirim RP."+ it.pos[0].costs[1].cost[0].value + "\nEstimasi kedatangan " + it.jne[0].costs[1].cost[0].etd + " hari"

            text31              = it.pos[0].costs[0].service + "\nOngkos Kirim RP."+ it.pos[0].costs[0].cost[0].value + "\nEstimasi kedatangan " + it.jne[0].costs[0].cost[0].etd + " hari"
            text32              = it.pos[0].costs[1].service + "\nOngkos Kirim RP."+ it.pos[0].costs[1].cost[0].value + "\nEstimasi kedatangan " + it.jne[0].costs[1].cost[0].etd + " hari"
        }
    }

    private fun r_initiation() {
        radioGroup = binding.radioGroup
        radioTiki = binding.radioGroup2
        radioPos = binding.radioGroup3
    }

    private fun r_radioGroup() {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById(checkedId) as RadioButton
            if (radioButton.text.equals(text11)){
                r_jne(11)
            }
            else if (radioButton.text.equals(text12)){
                r_jne(12)
            }
            else if (radioButton.text.equals(text13)){
                r_jne(13)
            }
        }
        radioTiki.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById(checkedId) as RadioButton
            if (radioButton.text.equals(text21)){
                r_tiki(21)
            }
            else if (radioButton.text.equals(text22)){
                r_tiki(22)
            }
        }
        radioPos.setOnCheckedChangeListener { group, chechkedId ->
            val radioButton = group.findViewById(chechkedId) as RadioButton
            if (radioButton.text.equals(text31)){
                pos(31)
            }
            else if (radioButton.text.equals(text32)){
                pos(32)
            }
        }

    }

    private fun r_jne(id: Int) {
        Log.e("TAG", "r_radioGroup: ID -> " + id)
        if (id.equals(11)) {
            kurir = courier.jne[0].code
            price = courier.jne[0].costs[0].cost[0].value.toString()
            service = courier.jne[0].costs[0].service
            estimate = courier.jne[0].costs[0].cost[0].etd
        } else if (id.equals(12)) {
            kurir = courier.jne[0].code
            price = courier.jne[0].costs[1].cost[0].value.toString()
            service = courier.jne[0].costs[1].service
            estimate = courier.jne[0].costs[1].cost[0].etd
        } else if (id.equals(13)) {
            kurir = courier.jne[0].code
            price = courier.jne[0].costs[2].cost[0].value.toString()
            service = courier.jne[0].costs[2].service
            estimate = courier.jne[0].costs[2].cost[0].etd
        }
    }

    private fun r_tiki(id: Int) {
        Log.e("TAG", "r_radioGroup: ID -> " + id)
        if (id.equals(21)) {
            kurir = courier.tiki[0].code
            price = courier.tiki[0].costs[0].cost[0].value.toString()
            service = courier.tiki[0].costs[0].service
            estimate = courier.tiki[0].costs[0].cost[0].etd
        } else if (id.equals(22)) {
            kurir = courier.tiki[0].code
            price = courier.tiki[0].costs[1].cost[0].value.toString()
            service = courier.tiki[0].costs[1].service
            estimate = courier.tiki[0].costs[1].cost[0].etd
        }
    }

    private fun pos(id: Int){
        Log.e("TAG", "r_radioGroup: ID -> " + id)
        if(id.equals(31)){
            kurir = courier.pos[0].code
            price = courier.pos[0].costs[0].cost[0].value.toString()
            service = courier.pos[0].costs[0].service
            estimate = courier.pos[0].costs[0].cost[0].etd
        } else if (id.equals(32)) {
            kurir = courier.pos[0].code
            price = courier.pos[0].costs[1].cost[0].value.toString()
            service = courier.pos[0].costs[1].service
            estimate = courier.pos[0].costs[1].cost[0].etd
        }
    }

    private fun r_button() {
        binding.verifyAcc.setOnClickListener {
            finish()
        }
        binding.btnOk.setOnClickListener {
            r_send()
        }
    }

    private fun r_send() {
        val i = Intent()
        i.putExtra("kurir", kurir)
        i.putExtra("service", service)
        i.putExtra("price", price)
        i.putExtra("estimate", estimate)
        setResult(Activity.RESULT_OK, i)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}