package com.fitri.jilbab.ui.midtrans

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.lifecycleScope
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.data.model.user.order.BodyPlaceOrder
import com.fitri.jilbab.databinding.ActivityMidtransBinding
import com.fitri.jilbab.ui.cart.CartViewModel
import com.midtrans.sdk.uikit.api.model.CustomColorTheme
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
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result?.resultCode == RESULT_OK) {
            result.data?.let {
                val transactionResult = it.getParcelableExtra<TransactionResult>(UiKitConstants.KEY_TRANSACTION_RESULT)
                Toast.makeText(this,"${transactionResult?.transactionId}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMidtransBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.paymentMidtrans(
                BodyPlaceOrder(
                    courier = "JNE",
                    courier_package = "OKE",
                    shipping_cost = "9000",
                    delivery_estimate = "2-3",
                    id_shipping_address = "7",
                    total_price = "35000"
                )
            )
        }

        setupObserver()
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
        initMidtransSdk()
    }

    private fun initMidtransSdk() {
        UiKitApi.Builder()
            .withMerchantClientKey("SB-Mid-client-tk7UewSkDrTjhV5S") // client_key is mandatory
            .withContext(this) // context is mandatory
            .withMerchantUrl("https://ecom-mobile.spdev.my.id/api/") // set transaction finish callback (sdk callback)
            .enableLog(true) // enable sdk log (optional)
            .withColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255"))
            .build()
        setLocaleNew("id") //`en` for English and `id` for Bahasa

        UiKitApi.getDefaultInstance().startPaymentUiFlow(
            this@MidtransActivity, // Activity
            launcher, // ActivityResultLauncher
            snapToken // Snap Token
        )
    }

    // function to set the SDK language
    private fun setLocaleNew(languageCode: String?) {
        val locales = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(locales)
    }
}