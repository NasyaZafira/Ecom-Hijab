package com.fitri.jilbab.ui.singup

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.fitri.jilbab.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.R
import com.fitri.jilbab.databinding.ActivitySignupBinding
import com.fitri.jilbab.ui.login.LoginActivity
import com.google.android.material.textfield.TextInputEditText
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
                    binding.boxName.error = "Nama tidak boleh kosong"
                else -> binding.boxName.error = null
            }
            validateButton()
        }
        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            when {
                text.isNullOrEmpty() ->
                    binding.boxMail.error = "Alamat email tidak boleh kosong"
                !Patterns.EMAIL_ADDRESS.matcher(text ?: "").matches() ->
                    binding.boxMail.error = "Invalid e-mail format"
                else -> binding.boxMail.error = null
            }
            validateButton()
        }
        binding.etPssword.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isEmpty() -> {
                    binding.boxPassword.error = "Kata sandi tidak boleh kosong"
                }
                else -> binding.boxPassword.error = null
            }
            validateButton()
        }

        binding.txtLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnContinue.setOnClickListener {
            lifecycleScope.launch {
                viewModel.signUp(
                    email = binding.etEmail.text.toString(),
                    name = binding.etName.text.toString(),
                    password = binding.etPssword.text.toString(),
                    role = "customer"
                )
            }
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
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
                Toast.makeText(this, "Berhasil mendaftar", Toast.LENGTH_LONG).show()
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            } else {
                Toast.makeText(this, "Gagal mendaftar, cek kembali data anda", Toast.LENGTH_LONG)
                    .show()
            }
        }
        viewModel.errorLog.observe(this) {
            binding.warningError.visibility = View.VISIBLE
            window.navigationBarColor = ContextCompat.getColor(this, R.color.colorError)

        }
    }
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is TextInputEditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    Log.e("focus", "touchevent")
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
            if (v is AutoCompleteTextView) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    Log.e("focus", "touchevent")
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}
