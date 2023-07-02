package com.fitri.jilbab.ui.admin.product_admin

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
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
import com.fitri.jilbab.databinding.ActivityAddProductBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class AddProductActivity : BaseActivity() {

    private lateinit var binding: ActivityAddProductBinding
    private val viewModel: ProductAdminVm by viewModels()
    private var selectedFiles: File? = null
    private var selectedFiles1: File? = null
    private var selectedFiles2: File? = null
    private var selectedFiles3: File? = null
    private var selectedFiles4: File? = null

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
        checked()
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
        binding.close01.setOnClickListener {
            binding.fixaddreportImage1.visibility = View.VISIBLE
            selectedFiles!!.delete()
            binding.fixaddreportImage1.setImageDrawable(null)
            binding.close01.visibility = View.INVISIBLE
            binding.fixaddreportTxt1.visibility = View.VISIBLE
            binding.textView18.visibility = View.VISIBLE
        }
        binding.close02.setOnClickListener {
            binding.fixaddreportImage2.visibility = View.VISIBLE
            selectedFiles!!.delete()
            binding.fixaddreportImage2.setImageDrawable(null)
            binding.close02.visibility = View.INVISIBLE
            binding.fixaddreportTxt2.visibility = View.VISIBLE
            binding.textView12.visibility = View.VISIBLE

        }
        binding.close03.setOnClickListener {
            binding.fixaddreportImage3.visibility = View.VISIBLE
            selectedFiles!!.delete()
            binding.fixaddreportImage3.setImageDrawable(null)
            binding.close03.visibility = View.INVISIBLE
            binding.fixaddreportTxt3.visibility = View.VISIBLE
            binding.textView13.visibility = View.VISIBLE

        }
        binding.close04.setOnClickListener {
            binding.fixaddreportImage4.visibility = View.VISIBLE
            selectedFiles!!.delete()
            binding.fixaddreportImage4.setImageDrawable(null)
            binding.close04.visibility = View.INVISIBLE
            binding.fixaddreportTxt4.visibility = View.VISIBLE
            binding.textView14.visibility = View.VISIBLE

        }
        binding.close05.setOnClickListener {
            binding.fixaddreportImage5.visibility = View.VISIBLE
            selectedFiles!!.delete()
            binding.fixaddreportImage5.setImageDrawable(null)
            binding.close05.visibility = View.INVISIBLE
            binding.fixaddreportTxt5.visibility = View.VISIBLE
            binding.textView15.visibility = View.VISIBLE

        }

    }

    private fun f_runCategory() {
        lifecycleScope.launch {
            viewModel.categoryList()
        }
        setupObserver()
    }

    private fun checked() {
        binding.etNameProduct.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isBlank() -> binding.edtNmProduct.error =
                    "Nama produk tidak boleh kosong"
                else -> binding.edtNmProduct.error = null
            }
            validateButton()
        }
        binding.editPrice.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isBlank() -> binding.edtPriceProduct.error =
                    "Harga produk tidak boleh kosong"
                else -> binding.edtPriceProduct.error = null
            }
            validateButton()
        }
        binding.editDiskon.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isBlank() -> binding.edtDiskon.error =
                    "Diskon produk tidak boleh kosong"
                else -> binding.edtDiskon.error = null
            }
            validateButton()
        }
        binding.autoCompleteTxtKategori.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isBlank() -> binding.edtKategori.error =
                    "Kategori produk tidak boleh kosong"
                else -> binding.edtKategori.error = null
            }
            validateButton()
        }
        binding.editBerat.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isBlank() -> binding.edtBerat.error =
                    "Berat produk tidak boleh kosong"
                else -> binding.edtBerat.error = null
            }
            validateButton()
        }
        binding.editStock.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isBlank() -> binding.edtStock.error =
                    "Stok produk tidak boleh kosong"
                else -> binding.edtStock.error = null
            }
            validateButton()
        }
        binding.editDesc.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isBlank() -> binding.edtDesc.error =
                    "Deskripsi produk tidak boleh kosong"
                else -> binding.edtDesc.error = null
            }
            validateButton()
        }
        binding.editColor.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isBlank() -> binding.edtDesc.error =
                    "Warna produk tidak boleh kosong"
                else -> binding.edtDesc.error = null
            }
            validateButton()
        }

    }

    private fun validateButton() {
        val nameOk = binding.edtNmProduct.error == null
        val hargaOk = binding.edtPriceProduct.error == null
        val diskonOk = binding.edtDiskon.error == null
        val kategoriOk = binding.edtKategori.error == null
        val beratOk = binding.edtBerat.error == null
        val stokOk = binding.edtStock.error == null
        val descOk = binding.edtDesc.error == null
        val colorOK = binding.edtColor.error == null
        binding.btnContinue.isEnabled =
            nameOk && hargaOk && diskonOk && kategoriOk && beratOk && stokOk && descOk && colorOK
    }

    override fun setupObserver() {
        loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) showLoading() else hideLoading()
        }
        viewModel.category.observe(this) {
            val result = it.data.toMutableList()
            for (i: Int in 0 until result.size) {
                listSpinner.add(it.data[i].category_name)
                listId.add(it.data[i].id_category)
            }
        }
        viewModel.product.observe(this) {
            Toast.makeText(this, "Berhasil Menambahkan Produk", Toast.LENGTH_LONG).show()
//            lifecycleScope.launch {
//                viewModel.categoryList()
//            }
//            val i = Intent(this, SuperActivity::class.java)
//            startActivity(i)
            finish()
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
//            val i = Intent(this, SuperActivity::class.java)
//            startActivity(i)
            finish()
        }
    }

    private fun f_continue() {
        binding.btnContinue.setOnClickListener {
            val name = binding.etNameProduct.text.toString().trim()
            val harga = binding.editPrice.text.toString().trim()
            val diskon = binding.editDiskon.text.toString().trim()
            val category = idValue //binding.autoCompleteTxtKategori.text.toString().trim()
            val berat = binding.editBerat.text.toString().trim()
            val stok = binding.editStock.text.toString().trim()
            val desc = binding.editDesc.text.toString().trim()
            val info = binding.editDescInfo.text.toString().trim()
            val color = binding.editColor.text.toString().trim()
            val stokColor = binding.editStokColor.text.toString().trim()
            if (!name.isNullOrBlank() && !harga.isNullOrBlank() && !diskon.isNullOrBlank() && !category.isNullOrBlank() && !berat.isNullOrBlank() && !stok.isNullOrBlank() && !desc.isNullOrBlank() && !color.isNullOrBlank())
                lifecycleScope.launch {
                    viewModel.addProduct(
                        selectedFiles,
                        selectedFiles1,
                        selectedFiles2,
                        selectedFiles3,
                        selectedFiles4,
                        name,
                        harga,
                        diskon,
                        category,
                        berat,
                        stok,
                        desc,
                        info,
                        color,
                        stokColor
                    )
                }
            else {
                Toast.makeText(this, "Lengkapi data produk", Toast.LENGTH_LONG)
                    .show()
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
                    files.forEach {
                        val file = File(it.mediaPath)
                        if (it.mediaType == UwMediaPickerMediaType.IMAGE) {
                            if (file.sizeInMb <= 2.0) {
                                selectedFiles = File(it.mediaPath)
                                Glide
                                    .with(this)
                                    .load(it.mediaPath)
                                    .into(binding.fixaddreportImage1)
                                binding.fixaddreportTxt1.visibility = View.INVISIBLE
                                binding.textView18.visibility = View.INVISIBLE
                                binding.close01.visibility = View.VISIBLE
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
                        files.forEach {
                            val file = File(it.mediaPath)
                            if (it.mediaType == UwMediaPickerMediaType.IMAGE) {
                                if (file.sizeInMb <= 2.0) {
                                    selectedFiles1 = File(it.mediaPath)
                                    Glide
                                        .with(this)
                                        .load(it.mediaPath)
                                        .into(binding.fixaddreportImage2)
                                    binding.fixaddreportTxt2.visibility = View.INVISIBLE
                                    binding.textView12.visibility = View.INVISIBLE
                                    binding.close02.visibility = View.VISIBLE
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
                        files.forEach {
                            val file = File(it.mediaPath)
                            if (it.mediaType == UwMediaPickerMediaType.IMAGE) {
                                if (file.sizeInMb <= 2.0) {
                                    selectedFiles2 = File(it.mediaPath)
                                    Glide
                                        .with(this)
                                        .load(it.mediaPath)
                                        .into(binding.fixaddreportImage3)
                                    binding.fixaddreportTxt3.visibility = View.INVISIBLE
                                    binding.close03.visibility = View.VISIBLE
                                    binding.textView13.visibility = View.INVISIBLE
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
                        files.forEach {
                            val file = File(it.mediaPath)
                            if (it.mediaType == UwMediaPickerMediaType.IMAGE) {
                                if (file.sizeInMb <= 2.0) {
                                    selectedFiles3 = File(it.mediaPath)
                                    Glide
                                        .with(this)
                                        .load(it.mediaPath)
                                        .into(binding.fixaddreportImage4)
                                    binding.fixaddreportTxt4.visibility = View.INVISIBLE
                                    binding.close04.visibility = View.VISIBLE
                                    binding.textView14.visibility = View.INVISIBLE
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
                        files.forEach {
                            val file = File(it.mediaPath)
                            if (it.mediaType == UwMediaPickerMediaType.IMAGE) {
                                if (file.sizeInMb <= 2.0) {
                                    selectedFiles4 = File(it.mediaPath)
                                    Glide
                                        .with(this)
                                        .load(it.mediaPath)
                                        .into(binding.fixaddreportImage5)
                                    binding.fixaddreportTxt5.visibility = View.INVISIBLE
                                    binding.close05.visibility = View.VISIBLE
                                    binding.textView15.visibility = View.INVISIBLE
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
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 777) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectFileUpload()
            } else if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestImage2()
            } else if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestImage3()
            } else if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestImage4()
            } else if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestImage5()
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

}