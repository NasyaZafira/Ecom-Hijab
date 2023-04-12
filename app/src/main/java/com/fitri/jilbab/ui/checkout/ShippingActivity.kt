package com.fitri.jilbab.ui.checkout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    private  var kurir: String = ""
    private  var service: String = ""
    private  var price: String = ""
    private lateinit var courier: Courier
    private lateinit var radioTiki: RadioGroup
    private lateinit var radioPos : RadioGroup
    private val viewModel: CartViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShippingBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

            binding.txtName.text = it.jne[0].code
            binding.txtName2.text = it.tiki[0].code
            binding.one.text =
                it.jne[0].code + it.jne[0].costs[0].service + it.jne[0].costs[0].cost[0].value
            binding.two.text =
                it.jne[0].code + it.jne[0].costs[1].service + it.jne[0].costs[1].cost[0].value
            binding.three.text =
                it.jne[0].code + it.jne[0].costs[2].service + it.jne[0].costs[2].cost[0].value

            binding.one2.text =
                it.tiki[0].code + it.tiki[0].costs[0].service + it.tiki[0].costs[0].cost[0].value
            binding.two2.text =
                it.tiki[0].code + it.tiki[0].costs[1].service + it.tiki[0].costs[1].cost[0].value

            binding.one3.text =
                it.pos[0].code + it.pos[0].costs[0].service + it.pos[0].costs[0].cost[0].value
            binding.two3.text =
                it.pos[0].code + it.pos[0].costs[1].service + it.pos[0].costs[1].cost[0].value
        }
    }

    private fun r_initiation() {
        radioGroup = binding.radioGroup
        radioTiki = binding.radioGroup2
        radioPos = binding.radioGroup3
    }

    private fun r_radioGroup() {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
//            val radioButton = group.findViewById(checkedId) as RadioButton
            //Log.e("TAG", "r_radioGroup: JNE -> " + radioButton.text )
            r_jne(checkedId)
        }
        radioTiki.setOnCheckedChangeListener { group, checkedId ->
//            val radioButton = group.findViewById(checkedId) as RadioButton
//            Log.e("TAG", "r_radioGroup: JNE -> " + radioButton.id )
            r_tiki(checkedId)
        }
        radioPos.setOnCheckedChangeListener { group, chechkedId ->
//            val radioButton = group.findViewById(chechkedId) as RadioButton
//            Log.e("TAG", "r_radioGroup: JNE -> " + radioButton.id )
            pos(chechkedId)
        }

    }

    private fun r_jne(id: Int) {
        Log.e("TAG", "r_radioGroup: ID -> " + id)
        if (id.equals(2131362293)) {
            kurir = courier.jne[0].code
            price = courier.jne[0].costs[0].cost[0].value.toString()
            service = courier.jne[0].costs[0].service
        } else if (id.equals(2131362494)) {
            kurir = courier.jne[0].code
            price = courier.jne[0].costs[1].cost[0].value.toString()
            service = courier.jne[0].costs[1].service
        } else if (id.equals(2131362461)) {
            kurir = courier.jne[0].code
            price = courier.jne[0].costs[2].cost[0].value.toString()
            service = courier.jne[0].costs[2].service
        }
    }

    private fun r_tiki(id: Int) {
        Log.e("TAG", "r_radioGroup: ID -> " + id)
        if (id.equals(2131362294)) {
            kurir = courier.tiki[0].code
            price = courier.tiki[0].costs[0].cost[0].value.toString()
            service = courier.tiki[0].costs[0].service
        } else if (id.equals(2131362495)) {
            kurir = courier.tiki[0].code
            price = courier.tiki[0].costs[1].cost[0].value.toString()
            service = courier.tiki[0].costs[1].service
        }
    }

    private fun pos(id: Int){
        Log.e("TAG", "r_radioGroup: ID -> " + id)
        if(id.equals(2131362295)){
            kurir = courier.pos[0].code
            price = courier.pos[0].costs[0].cost[0].value.toString()
            service = courier.pos[0].costs[0].service
        } else if (id.equals(2131362496)) {
            kurir = courier.pos[0].code
            price = courier.pos[0].costs[1].cost[0].value.toString()
            service = courier.pos[0].costs[1].service
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
        setResult(Activity.RESULT_OK, i)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}