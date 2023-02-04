package com.fitri.jilbab.ui.singup

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.register.SignUpBody
import com.fitri.jilbab.databinding.ActivitySignupBinding
import com.fitri.jilbab.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@AndroidEntryPoint
class SignUpActivity : BaseActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etName.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isEmpty() ->
                    binding.boxName.error = "Name cannot be empty"
                else -> binding.boxName.error = null
            }
            validateButton()
        }
        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            when {
                text.isNullOrEmpty() ->
                    binding.boxMail.error = "Email cannot be empty"
                !Patterns.EMAIL_ADDRESS.matcher(text ?: "").matches() ->
                    binding.boxMail.error = "Invalid e-mail format"
                else -> binding.boxMail.error = null
            }
            validateButton()
        }
        binding.etPssword.doOnTextChanged { text, _, _, _ ->
            val txt = text.toString().trim()
            when {
                txt.isEmpty() ->
                    binding.txtPassword.error = "Password cannot be empty"
                !isPasswordValid(txt) ->
                    binding.txtPassword.error =
                        "The password must be at least 8 characters with a combination of letters, numbers and one capital letter"
                else -> {
                    binding.txtPassword.error = null
                }
            }
            validateButton()
        }

        binding.btnContinue.setOnClickListener {
            lifecycleScope.launch {
                viewModel.signUp(
                    email = binding.etEmail.text.toString(),
                    name = binding.etName.text.toString(),
                    password = binding.etPssword.text.toString(),
                    role = "user"
                )
            }
        }

        setupObserver()
    }

    private fun validateButton() {
        val nameOK = binding.boxName.error == null
        val emailOK = binding.boxMail.error == null
        val passOK = binding.boxPassword.error == null

        binding.btnContinue.isEnabled =
            nameOK && emailOK && passOK
    }

    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z]).{8,}$"
        val pattern = Pattern.compile(passwordPattern)
        val matcher = pattern.matcher(password)

        return matcher.matches()
    }

    override fun setupObserver() {
        loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) showLoading() else hideLoading()
        }

        viewModel.signUpResponse.observe(this) {
            if (it.success) {
                Toast.makeText(this, "Successful Registration", Toast.LENGTH_LONG).show()
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            } else {
                Toast.makeText(this, "Gagal Registrasi, Cek kembali data anda", Toast.LENGTH_LONG)
                    .show()
            }
        }
        viewModel.errorLog.observe(this) {
            binding.warningError.visibility = View.VISIBLE
            window.navigationBarColor = ContextCompat.getColor(this, R.color.colorError)

        }
    }
}
