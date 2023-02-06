package com.fitri.jilbab.ui.login

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.MainActivity
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.login.LoginBody
import com.fitri.jilbab.databinding.ActivitySigninBinding
import com.fitri.jilbab.ui.singup.SignUpActivity
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivitySigninBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
            val i = Intent(this, SignUpActivity::class.java)
            startActivity(i)
            finish()
        }


        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isEmpty() -> {
                    binding.boxMail.error = "Email required"
                }
//                !Patterns.EMAIL_ADDRESS.matcher(text ?: "").matches() ->
//                    binding.textField.error = "Format email tidak valid"
                else -> binding.boxMail.error = null
            }
            validateButton()
        }
        binding.etPssword.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isEmpty() -> {
                    binding.boxPassword.error = "Password required"
                }
                else -> binding.boxPassword.error = null
            }
            validateButton()
        }

        binding.btnSignin.setOnClickListener {
            lifecycleScope.launch {
                viewModel.login(
                    binding.etEmail.text.toString().trim(),
                    binding.etPssword.text.toString().trim()
                )
            }
        }
        setupObserver()

    }

    private fun validateButton() {
        val emailOK = binding.boxMail.error == null
        val passOK = binding.boxPassword.error == null
        binding.btnSignin.isEnabled = emailOK && passOK
    }

    override fun setupObserver() {
        loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) showLoading() else hideLoading()
        }
        viewModel.succesData.observe(this) {
            Toast.makeText(this@LoginActivity, "Berhasil masuk", Toast.LENGTH_SHORT).show()

            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finishAffinity()
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