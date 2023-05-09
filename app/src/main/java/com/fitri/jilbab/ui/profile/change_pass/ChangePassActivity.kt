package com.fitri.jilbab.ui.profile.change_pass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.MainActivity
import com.fitri.jilbab.R
import com.fitri.jilbab.databinding.ActivityChangePassBinding
import com.fitri.jilbab.databinding.ActivityEditProfileBinding
import com.fitri.jilbab.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangePassActivity : BaseActivity() {

    private lateinit var binding: ActivityChangePassBinding
    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(R.style.Theme_FitriJilbab_Home)

        binding.verifyAcc.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.isPassLatest.doOnTextChanged { text, _, _, _ ->
            when {
                text.isNullOrEmpty() ->
                    binding.edtPassLatest.error = "Kata Sandi tidak boleh kosong"
                else -> binding.edtPassLatest.error = null
            }
            validateButton()
        }
        binding.isPassNew.doOnTextChanged { text, _, _, _ ->
            val txt = text.toString().trim()
            when {
                txt.isEmpty() ->
                    binding.edtPasswordNew.error = "Kata sandi tidak boleh kosong"
                txt != binding.isRepas.text.toString() -> {
                    binding.edtRepass.error = "Konfirmasi kata sandi tidak sama"
                    binding.edtPasswordNew.error = null
                }
                else -> {
                    binding.edtPasswordNew.error = null
                    binding.edtRepass.error = null
                }
            }
            validateButton()
        }
        binding.isRepas.doOnTextChanged { text, _, _, _ ->
            when {
                text.isNullOrEmpty() ->
                    binding.edtRepass.error = "Konfirmasi kata sandi tidak boleh kosong"
                !text.isNullOrEmpty() && text.toString() != binding.isRepas.text.toString() ->
                    binding.edtRepass.error = "Konfirmasi kata sandi tidak sama"
                else -> binding.edtRepass.error = null
            }
            validateButton()
        }
        binding.btnContinue.setOnClickListener {
            lifecycleScope.launch{
                viewModel.updatePass(
                    confirmpassword = binding.isRepas.text.toString(),
                    currentpassword = binding.isPassLatest.text.toString(),
                    newpassword = binding.isPassNew.text.toString()
                )
            }
        }
        setupObserver()
    }

    private fun validateButton() {
        val lateOk = binding.edtPassLatest.error == null
        val newOK = binding.isPassNew.error == null
        val reOK = binding.isPassNew.error == null

        binding.btnContinue.isEnabled =
            lateOk && newOK && reOK
    }
    override fun setupObserver() {
        loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) showLoading() else hideLoading()
        }
        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
        viewModel.changePass.observe(this) {
            if (it.success == true) {
                Toast.makeText(this, "Berhasil Mengubah Kata Sandi", Toast.LENGTH_LONG).show()
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            } else if (it.success == false ){
                Toast.makeText(this, "Gagal Mengubah Kata Sandi", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}