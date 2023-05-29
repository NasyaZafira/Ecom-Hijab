package com.fitri.jilbab.ui.midtrans

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.lifecycleScope
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.MainActivity
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.user.order.BodyPlaceOrder
import com.fitri.jilbab.databinding.ActivityMidtransBinding
import com.fitri.jilbab.ui.cart.CartViewModel
import com.fitri.jilbab.ui.splash.Splash
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.midtrans.sdk.uikit.api.model.TransactionResult
import com.midtrans.sdk.uikit.external.UiKitApi
import com.midtrans.sdk.uikit.internal.util.UiKitConstants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MidtransActivity : BaseActivity() {

    private lateinit var binding: ActivityMidtransBinding
    private val viewModel: CartViewModel by viewModels()
    private var snapToken = ""
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result?.resultCode == RESULT_OK) {
                result.data?.let {
                    val transactionResult = it.getParcelableExtra<TransactionResult>(UiKitConstants.KEY_TRANSACTION_RESULT)
//                    Toast.makeText(this,"${transactionResult?.transactionId}", Toast.LENGTH_LONG).show()
                    Toast.makeText(this,"${transactionResult?.status}", Toast.LENGTH_SHORT).show()
                    when (transactionResult?.status) {
                        "success" -> {
                            val i = Intent(this, Splash::class.java)
                            startActivity(i)
                            finishAffinity()
                        }
                        "pending" -> {
                            initMidtransSdk()
                        }
                        else -> {
                            runData()
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMidtransBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(R.style.Theme_FitriJilbab_Home)

        runData()
    }

    override fun setupObserver() {
        loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) showLoading() else hideLoading()
        }
        viewModel.placeOrder.observe(this) {
            snapToken = it.data.snaptoken
            initMidtransSdk()
        }
    }

    override fun onResume() {
        super.onResume()
        launcher
    }

    private fun runData() {
        val data = intent.getParcelableExtra<BodyPlaceOrder>("midtrans")!!
        lifecycleScope.launch {
            viewModel.paymentMidtrans(
                BodyPlaceOrder(
                    courier = data.courier,
                    courier_package = data.courier_package,
                    shipping_cost = data.shipping_cost,
                    delivery_estimate = data.delivery_estimate,
                    id_shipping_address = data.id_shipping_address,
                    total_price = data.total_price
                )
            )
        }

        setupObserver()
    }

    private fun initMidtransSdk() {

        UiKitApi.Builder()
            .withMerchantClientKey("SB-Mid-client-tk7UewSkDrTjhV5S") // client_key is mandatory
            .withContext(this) // context is mandatory
            .withMerchantUrl("https://ecom-mobile.spdev.my.id/api/") // set transaction finish callback (sdk callback)
            .enableLog(true) // enable sdk log (optional)
            .build()
        setLocaleNew("id") //`en` for English and `id` for Bahasa

        UiKitApi.getDefaultInstance().startPaymentUiFlow(
            this@MidtransActivity, // Activity
            launcher, // ActivityResultLauncher
            snapToken // Snap Token
        )

        SdkUIFlowBuilder.init()
    }

    // function to set the SDK language
    private fun setLocaleNew(languageCode: String?) {
        val locales = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(locales)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}