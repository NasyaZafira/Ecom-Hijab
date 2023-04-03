package com.fitri.jilbab.ui.admin.product_admin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.anilokcun.uwmediapicker.UwMediaPicker
import com.anilokcun.uwmediapicker.model.UwMediaPickerMediaType
import com.bumptech.glide.Glide
import com.commer.app.base.BaseActivity
import com.fitri.jilbab.MainActivity
import com.fitri.jilbab.R
import com.fitri.jilbab.databinding.ActivityAddProductBinding
import com.fitri.jilbab.ui.admin.SuperActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class AddProductActivity : BaseActivity() {

    private lateinit var binding: ActivityAddProductBinding
    private val viewModel: ProductAdminVm by viewModels()
    private var selectedFiles = mutableListOf<File>()
    private var selectedFiles1 = mutableListOf<File>()
    private var selectedFiles2 = mutableListOf<File>()
    private var selectedFiles3 = mutableListOf<File>()
    private var selectedFiles4 = mutableListOf<File>()

    private val File.size get() = if (!exists()) 0.0 else length().toDouble()
    private val File.sizeInKb get() = size / 1024
    private val File.sizeInMb get() = sizeInKb / 1024

    private val listSpinner: MutableList<String> = ArrayList()
    private val listId: MutableList<Int> = ArrayList()
    private var idValue: String = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        f_back()
        f_continue()
        f_runCategory()
        f_listSpinner()

        binding.layout1.setOnClickListener {
            Log.e("TAG", "f_imageUpload: " + it)
            requestAccessForFile()
        }
        binding.layout2.setOnClickListener {
            requestImage2()
        }
        binding.layout3.setOnClickListener {
            requestImage3()
        }
        binding.layout4.setOnClickListener {
            requestImage4()
        }
        binding.layout5.setOnClickListener {
            requestImage5()
        }
    }

    private fun f_runCategory() {
        lifecycleScope.launch {
            viewModel.categoryList()
        }
        setupObserver()
    }

    override fun setupObserver() {
        viewModel.category.observe(this) {
            val result = it.data.toMutableList()
            for (i: Int in 0 until result.size) {
                listSpinner.add(it.data[i].category_name)
                listId.add(it.data[i].id_category)
            }
            viewModel.product.observe(this) {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            }
        }
    }

    private fun f_listSpinner() {

        val arrayAdapterKategori = ArrayAdapter(this, R.layout.signup_menu, listSpinner)
        val autoCompleteKategori = binding.autoCompleteTxtKategori
        autoCompleteKategori.setAdapter(arrayAdapterKategori)

        binding.autoCompleteTxtKategori.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            idValue = listId.get(position).toString()
            Log.e("TAG", "f_listSpinner: " + idValue)
        })
    }

    private fun f_back() {
        binding.verifyAcc.setOnClickListener {
            Toast.makeText(this, "Berhasil Menambahkan Produk", Toast.LENGTH_LONG).show()
            val i = Intent(this, SuperActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun f_continue() {
        binding.btnContinue.setOnClickListener {
            lifecycleScope.launch {
                val name = binding.etNameProduct.text.toString().trim()
                val harga = binding.editPrice.text.toString().trim()
                val diskon = binding.editDiskon.text.toString().trim()
                val category = idValue //binding.autoCompleteTxtKategori.text.toString().trim()
                val berat = binding.editBerat.text.toString().trim()
                val stok = binding.editStock.text.toString().trim()
                val desc = binding.editDesc.text.toString().trim()
                val info = binding.editDescInfo.text.toString().trim()
                if (selectedFiles.isNotEmpty()) {
                    viewModel.addProduct(
                        selectedFiles[0],
                        selectedFiles1[0],
                        selectedFiles2[0],
                        selectedFiles3[0],
                        selectedFiles4[0],
                        name,
                        harga,
                        diskon,
                        category,
                        berat,
                        stok,
                        desc,
                        info
                    )
                }
            }
        }
    }

    private fun requestAccessForFile() {
        if (ContextCompat.checkSelfPermission(
                this@AddProductActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@AddProductActivity,
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
                    selectedFiles.clear()
                    files.forEach {
                        val file = File(it.mediaPath)
                        if (it.mediaType == UwMediaPickerMediaType.IMAGE) {
                            if (file.sizeInMb <= 50.0) {
                                selectedFiles.add(File(it.mediaPath))
                                Glide
                                    .with(this)
                                    .load(it.mediaPath)
                                    .into(binding.fixaddreportImage1)
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


    private fun requestImage2() {
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
                        selectedFiles1.clear()
                        files.forEach {
                            val file = File(it.mediaPath)
                            if (it.mediaType == UwMediaPickerMediaType.IMAGE) {
                                if (file.sizeInMb <= 50.0) {
                                    selectedFiles1.add(File(it.mediaPath))
                                    Glide
                                        .with(this)
                                        .load(it.mediaPath)
                                        .into(binding.fixaddreportImage2)
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
    }

    private fun requestImage3() {
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
                        selectedFiles2.clear()
                        files.forEach {
                            val file = File(it.mediaPath)
                            if (it.mediaType == UwMediaPickerMediaType.IMAGE) {
                                if (file.sizeInMb <= 50.0) {
                                    selectedFiles2.add(File(it.mediaPath))
                                    Glide
                                        .with(this)
                                        .load(it.mediaPath)
                                        .into(binding.fixaddreportImage3)
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
    }

    private fun requestImage4() {
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
                        selectedFiles3.clear()
                        files.forEach {
                            val file = File(it.mediaPath)
                            if (it.mediaType == UwMediaPickerMediaType.IMAGE) {
                                if (file.sizeInMb <= 50.0) {
                                    selectedFiles3.add(File(it.mediaPath))
                                    Glide
                                        .with(this)
                                        .load(it.mediaPath)
                                        .into(binding.fixaddreportImage4)
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
    }

    private fun requestImage5() {
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
                        selectedFiles4.clear()
                        files.forEach {
                            val file = File(it.mediaPath)
                            if (it.mediaType == UwMediaPickerMediaType.IMAGE) {
                                if (file.sizeInMb <= 50.0) {
                                    selectedFiles4.add(File(it.mediaPath))
                                    Glide
                                        .with(this)
                                        .load(it.mediaPath)
                                        .into(binding.fixaddreportImage5)
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
    }
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        if (requestCode == 777) {
//            if (
//                grantResults.isNotEmpty() && grantResults[0] ==
//                PackageManager.PERMISSION_GRANTED
//            ) {
//                requestImage1()
//            } else {
//                Toast.makeText(
//                    this,
//                    "Aplikasi ini butuh izin akses",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }

}