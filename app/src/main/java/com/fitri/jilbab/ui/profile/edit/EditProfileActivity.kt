package com.fitri.jilbab.ui.profile.edit

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.anilokcun.uwmediapicker.UwMediaPicker
import com.anilokcun.uwmediapicker.model.UwMediaPickerMediaType
import com.bumptech.glide.Glide
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.MainActivity
import com.fitri.jilbab.R
import com.fitri.jilbab.data.model.profile.Data
import com.fitri.jilbab.databinding.ActivityEditProfileBinding
import com.fitri.jilbab.ui.profile.ProfileViewModel
import com.fitri.jilbab.ui.profile.change_pass.ChangePassActivity
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var isData: Data
    private var selectedFiles = mutableListOf<File>()

    private val File.size get() = if (!exists()) 0.0 else length().toDouble()
    private val File.sizeInKb get() = size / 1024
    private val File.sizeInMb get() = sizeInKb / 1024

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        f_back()
        f_detail()
        f_pick()
        f_extras()
        f_save()
        f_changepw()
        setupObserver()
        birth()
    }

    private fun f_back() {
        binding.verifyAcc.setOnClickListener {
           onBackPressed()
        }
    }

    private fun f_detail() {
        lifecycleScope.launch {
            viewModel.detailProfile()
        }
    }

    private fun f_pick() {
        binding.btnPic.setOnClickListener {
            requestAccessForFile()
        }
    }

    private fun f_extras() {
        intent.extras?.getParcelable<Data>("data")?.let {
            isData = it
            binding.editFullName.setText(isData.name)
            binding.editPhoneNumber.setText(isData.detail?.phone)
            binding.autoCompleteTxtGender.setText(isData.detail?.gender.toString())
            binding.editBirth.setText(isData.detail?.date_of_birth)
            binding.editAdress.setText(isData.detail?.address)

            if (it.detail?.profile_picture == null) {
                Glide.with(binding.imgProfile.context)
                    .load(R.drawable.white_image)
                    .error(R.drawable.white_image)
                    .into(binding.imgProfile)
            } else {
                Glide.with(binding.imgProfile.context)
                    .load("https://ecom-mobile.spdev.my.id/img/profile/" + it.detail!!.profile_picture)
                    .error(R.drawable.white_image)
                    .into(binding.imgProfile)
            }
        }
    }

    private fun f_save() {
        binding.btnSave.setOnClickListener {
            lifecycleScope.launch {
                val nama = binding.editFullName.text.toString().trim()
                val numb = binding.editPhoneNumber.text.toString().trim()
                val gender = binding.autoCompleteTxtGender.text.toString()
                val birth = binding.editBirth.text.toString().trim()
                val adress = binding.editAdress.text.toString().trim()
                if (selectedFiles.isNotEmpty()) {
                    viewModel.isAva(selectedFiles[0])
                }
                if (!nama.isNullOrBlank() && !numb.isNullOrBlank() && !gender.isNullOrBlank() && !birth.isNullOrBlank() && !adress.isNullOrBlank()) {
                    viewModel.editProfile(adress, birth, gender, nama, numb)
                } else {
                    Toast.makeText(this@EditProfileActivity, "Lengkapi Data Akun", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun birth() {
        var cal = Calendar.getInstance()
        var textview_date: TextInputEditText? = null
        textview_date = this.binding.editBirth
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "yyyy-MM-dd" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                textview_date.setText(sdf.format(cal.time))
                Log.e("TAG", "r_dateBirth: " + SimpleDateFormat(myFormat, Locale.US))
            }

        binding.editBirth.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Log.e("TAG", "r_dateBirth: has focus if")
                binding.editBirth.clearFocus()
                binding.editBirth.clearFocus()
                textview_date.inputType = InputType.TYPE_NULL
                DatePickerDialog(
                    this,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            } else {
                Log.e("TAG", "r_dateBirth: has focus else")
            }
        }
    }

    private fun f_changepw() {
        binding.btnChange.setOnClickListener {
            val i = Intent(this@EditProfileActivity, ChangePassActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun setupObserver() {
        loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) showLoading() else hideLoading()
        }

        viewModel.updateProfile.observe(this) {
            Log.e("TAG", "setupObserver: " + it)
            Toast.makeText(
                this@EditProfileActivity,
                "Berhasil Mengubah Informasi Akun",
                Toast.LENGTH_LONG
            ).show()
//            val i = Intent(this, MainActivity::class.java)
//            startActivity(i)
            finish()
        }

        viewModel.userDetail.observe(this) {
        }

        viewModel.changeava.observe(this) {
            Log.e("TAG", "setupObserver: " + it)
            Toast.makeText(this, "Berhasil Mengubah Foto Profil", Toast.LENGTH_LONG).show()
//            val i = Intent(this@EditProfileActivity, MainActivity::class.java)
//            startActivity(i)
            finish()

        }

    }

    override fun onResume() {
        super.onResume()
        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapterGender = ArrayAdapter(this, R.layout.signup_menu, gender)
        val autoCompleteGender = binding.autoCompleteTxtGender
        autoCompleteGender.setAdapter(arrayAdapterGender)
    }

    private fun requestAccessForFile() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                777
            )
        } else {
            selectFileUpload()
        }
    }

    private fun selectFileUpload() {
        UwMediaPicker
            .with(this)
            .setLightStatusBar(true)
            .setMaxSelectableMediaCount(1)
            .setGalleryMode(UwMediaPicker.GalleryMode.ImageAndVideoGallery)
            .setGridColumnCount(2)
            .enableImageCompression(true)
            .setCompressFormat(Bitmap.CompressFormat.JPEG)
            .setCompressionMaxWidth(709F)
            .setCompressionMaxHeight(640F)
            .setCompressionQuality(50)
            .setCompressedFileDestinationPath(this@EditProfileActivity.filesDir.path)
            .launch { f ->
                f?.let { files ->
                    selectedFiles.clear()
                    files.forEach {
                        val file = File(it.mediaPath)
                        if (it.mediaType == UwMediaPickerMediaType.IMAGE) {
                            if (file.sizeInMb <= 2.0) {
                                selectedFiles.add(File(it.mediaPath))
                                Glide
                                    .with(this)
                                    .load(it.mediaPath)
                                    .into(binding.imgProfile)
                            } else {
                                Toast.makeText(
                                    this,
                                    "Maksimum foto yang dipilih harus < 2 MB",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 777) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectFileUpload()
            } else {
                Toast.makeText(
                    this,
                    "Aplikasi ini butuh izin akses",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}






