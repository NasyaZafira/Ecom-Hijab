package com.fitri.jilbab.ui.admin.home.category

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.anilokcun.uwmediapicker.UwMediaPicker
import com.anilokcun.uwmediapicker.model.UwMediaPickerMediaType
import com.bumptech.glide.Glide
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.CustomLoadingDialog
import com.fitri.jilbab.R
import com.fitri.jilbab.databinding.ActivityAddCategoryBinding
import com.fitri.jilbab.ui.admin.SuperActivity
import com.fitri.jilbab.ui.admin.product_admin.ProductAdminVm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class AddCategoryActivity : BaseActivity() {

    private lateinit var binding: ActivityAddCategoryBinding
    private val viewModel: ProductAdminVm by viewModels()
    private val File.size get() = if (!exists()) 0.0 else length().toDouble()
    private val File.sizeInKb get() = size / 1024
    private val File.sizeInMb get() = sizeInKb / 1024
    private var selectedFiles = mutableListOf<File>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checked()
        binding.imgCat.setOnClickListener {
            requestAccessForFile()
        }
        binding.close.setOnClickListener {
            binding.imgCat.visibility = View.VISIBLE
            selectedFiles!!.clear()
            binding.imgCat.setImageDrawable(null)
            binding.close.visibility = View.INVISIBLE
            binding.imgCat.visibility = View.VISIBLE
            binding.txtImg.visibility = View.VISIBLE
        }
        binding.verifyAcc.setOnClickListener {
            val i = Intent(this, SuperActivity::class.java)
            startActivity(i)
            finish()
        }
        binding.btnContinue.setOnClickListener {
            val name = binding.isedtName.text.toString().trim()
            if (!name.isNullOrBlank() && selectedFiles.isNotEmpty()) {
                lifecycleScope.launch {
                    viewModel.addCategory(name, selectedFiles[0])
                }
            } else {
                Toast.makeText(this, "Lengkapi nama kategori dan foto kategori", Toast.LENGTH_LONG).show()

            }
        }
        setupObserver()
    }

    override fun setupObserver() {
        loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) showLoading() else hideLoading()
        }
        viewModel.addCat.observe(this) {
            if (it.success == false) {
                binding.warningError.visibility = View.VISIBLE
            }
            Log.e("TAG", "setupObserver: " + it.data)
            Toast.makeText(this, "Berhasil Menambahkan Kategori", Toast.LENGTH_LONG).show()
            val i = Intent(this, SuperActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun checked() {
        binding.isedtName.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isEmpty() -> binding.edtNmProduct.error =
                    "Nama kategori tidak boleh kosong"
                else -> binding.edtNmProduct.error = null
            }
        }
    }

    private fun requestAccessForFile() {
        if (ContextCompat.checkSelfPermission(
                this@AddCategoryActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@AddCategoryActivity,
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
            .setGalleryMode(UwMediaPicker.GalleryMode.ImageGallery)
            .setGridColumnCount(2)
            .enableImageCompression(true)
            .setCompressFormat(Bitmap.CompressFormat.JPEG)
            .setCompressionMaxWidth(709F)
            .setCompressionMaxHeight(640F)
            .setCompressionQuality(50)
            .setCompressedFileDestinationPath(this.filesDir.path)
            .launch { f ->
                f?.let { files ->
                    files.forEach {
                        val file = File(it.mediaPath)
                        if (it.mediaType == UwMediaPickerMediaType.IMAGE) {
                            if (file.sizeInMb <= 2.0) {
                                selectedFiles.add(File(it.mediaPath))
                                Glide
                                    .with(this)
                                    .load(it.mediaPath)
                                    .into(binding.imgCat)
                                binding.txtImg.visibility = View.INVISIBLE
                                binding.close.visibility = View.VISIBLE
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
        val i = Intent(this, SuperActivity::class.java)
        startActivity(i)
        finish()
    }
}