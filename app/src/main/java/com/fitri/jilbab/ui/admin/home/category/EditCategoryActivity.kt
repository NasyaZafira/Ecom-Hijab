package com.fitri.jilbab.ui.admin.home.category

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
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
import com.fitri.jilbab.data.model.admin.category.Data
import com.fitri.jilbab.databinding.ActivityEditCategoryBinding
import com.fitri.jilbab.ui.admin.SuperActivity
import com.fitri.jilbab.ui.admin.product_admin.ProductAdminVm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class EditCategoryActivity : BaseActivity() {

    private lateinit var binding: ActivityEditCategoryBinding
    private val viewModel: ProductAdminVm by viewModels()
    private val File.size get() = if (!exists()) 0.0 else length().toDouble()
    private val File.sizeInKb get() = size / 1024
    private val File.sizeInMb get() = sizeInKb / 1024
    private var selectedFiles = mutableListOf<File>()
    private lateinit var data: Data
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checked()
        intentExtras()

        binding.imgCat.setOnClickListener {
            requestAccessForFile()
        }
        binding.verifyAcc.setOnClickListener {
            val i = Intent(this, SuperActivity::class.java)
            startActivity(i)
            finish()
        }
        binding.close.setOnClickListener {
            binding.imgCat.visibility = View.VISIBLE
            selectedFiles!!.clear()
            binding.imgCat.setImageDrawable(null)
            binding.close.visibility = View.INVISIBLE
            binding.imgCat.visibility = View.VISIBLE
            binding.txtImg.visibility = View.VISIBLE
        }

        setupObserver()

    }

    private fun intentExtras() {
        intent.extras?.getParcelable<Data>("category")?.let {
            data = it
            binding.isedtName.setText(data.category_name)
            if (!data.category_image.isNullOrBlank()) {
                Glide.with(binding.imgCat.context)
                    .load("https://ecom-mobile.spdev.my.id/img/category/" + data.category_image)
                    .error(R.drawable.white_image)
                    .into(binding.imgCat)
                binding.close.visibility = View.VISIBLE
                binding.txtImg.visibility = View.INVISIBLE
            } else {
                Glide.with(binding.imgCat.context)
                    .load(R.drawable.white_image)
                    .error(R.drawable.white_image)
                    .into(binding.imgCat)
                binding.close.visibility = View.INVISIBLE
                binding.txtImg.visibility = View.VISIBLE
            }
            binding.btnContinue.setOnClickListener {
                val id = data.id_category
                val name = binding.isedtName.text.toString().trim()
                if (!name.isNullOrBlank() && selectedFiles.isNotEmpty()) {
                    lifecycleScope.launch {
                        viewModel.editCategory(id, name, selectedFiles[0])
                    }
                } else {
                    Toast.makeText(this, "Lengkapi nama kategori dan foto kategori", Toast.LENGTH_LONG)
                        .show()

                }
            }
            binding.btnDel.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.deletecCategory(data.id_category)
                }
            }
        }
    }

    override fun setupObserver() {
        loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) showLoading() else hideLoading()
        }
        viewModel.editCat.observe(this){
            Toast.makeText(this, "Berhasil Mengubah Kategori", Toast.LENGTH_LONG).show()
            val i = Intent(this, SuperActivity::class.java)
            startActivity(i)
            finish()
        }
        viewModel.delCat.observe(this){
            Toast.makeText(this, "Berhasil Menghapus Kategori", Toast.LENGTH_LONG).show()
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
                            if (file.sizeInMb <= 50.0) {
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
                                    "Maksimum foto yang dipilih harus < 50 MB",
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