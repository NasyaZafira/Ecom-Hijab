package com.fitri.jilbab.ui.checkout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.fitri.jilbab.data.model.user.co.Courier
import com.fitri.jilbab.data.model.user.co.Data
import com.fitri.jilbab.databinding.ActivityShippingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShippingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShippingBinding
    private lateinit var radioGroup: RadioGroup
    private lateinit var kurir: String
    private lateinit var service: String
    private lateinit var price: String
    private lateinit var courier: Courier
    private lateinit var radioTiki: RadioGroup
    private lateinit var radioPos : RadioGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShippingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        r_intent()
        r_initiation()
        r_radioGroup()
        r_button()

    }

    private fun r_intent() {
        intent.extras!!.getParcelable<Courier>("shipping").let {
            Log.e("TAG", "r_intent: " + it!!)
            courier = it
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
            binding.three2.text =
                it.tiki[0].code + it.tiki[0].costs[2].service + it.tiki[0].costs[2].cost[0].value

            binding.one3.text =
                it.pos[0].code + it.pos[0].costs[0].service + it.pos[0].costs[0].cost[0].value
            binding.two3.text =
                it.pos[0].code + it.pos[0].costs[1].service + it.pos[0].costs[1].cost[0].value
            binding.three3.text =
                it.pos[0].code + it.pos[0].costs[2].service + it.pos[0].costs[2].cost[0].value
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
            //Log.e("TAG", "r_radioGroup: JNE -> " + radioButton.text )
            r_jne(checkedId)
        }
        radioTiki.setOnCheckedChangeListener { group, checkedId ->

        }
        radioPos.setOnCheckedChangeListener { group, chechkedId ->  }

    }

    private fun r_jne(id: Int) {
        Log.e("TAG", "r_radioGroup: ID -> " + id)
        if (id.equals(2131362261)) {
            kurir = courier.jne[0].code
            price = courier.jne[0].costs[0].cost[0].value.toString()
            service = courier.jne[0].costs[0].service
        } else if (id.equals(2131362463)) {
            kurir = courier.jne[0].code
            price = courier.jne[0].costs[1].cost[0].value.toString()
            service = courier.jne[0].costs[1].service
        } else if (id.equals(2131362428)) {
            kurir = courier.jne[0].code
            price = courier.jne[0].costs[2].cost[0].value.toString()
            service = courier.jne[0].costs[2].service
        }
    }

    private fun r_tiki(id: Int) {
        Log.e("TAG", "r_radioGroup: ID -> " + id)
        if (id.equals(2131362261)) {
            kurir = courier.tiki[0].code
            price = courier.tiki[0].costs[0].cost[0].value.toString()
            service = courier.tiki[0].costs[0].service
        } else if (id.equals(2131362463)) {
            kurir = courier.tiki[0].code
            price = courier.tiki[0].costs[1].cost[0].value.toString()
            service = courier.jne[0].costs[1].service
        } else if (id.equals(2131362428)) {
            kurir = courier.tiki[0].code
            price = courier.tiki[0].costs[2].cost[0].value.toString()
            service = courier.tiki[0].costs[2].service
        }
    }

    private fun r_button() {
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

}