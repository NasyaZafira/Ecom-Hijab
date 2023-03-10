package com.fitri.jilbab.ui.admin.product_admin

import android.webkit.MimeTypeMap
import androidx.lifecycle.MutableLiveData
import com.commer.app.base.BaseViewModel
import com.fitri.jilbab.data.model.admin.category.CategoryListResponse
import com.fitri.jilbab.data.model.admin.product.add.AddProductResponse
import com.fitri.jilbab.data.model.admin.product.list.ListProductResponse
import com.fitri.jilbab.repository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProductAdminVm @Inject constructor(
    private val productRepository: AdminRepository
) : BaseViewModel() {

    val succesLoad = MutableLiveData<String>()
    val list = MutableLiveData<ListProductResponse>()
    val category = MutableLiveData<CategoryListResponse>()
    val product = MutableLiveData<AddProductResponse>()

    suspend fun theList() {
        productRepository.adminProduct(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
        ).collect {
            list.postValue(it)
            succesLoad.postValue("200")
        }
    }
    suspend fun categoryList() {
        productRepository.categoryAdmin(
            onStart = {
                _loading.postValue(true)
            },
            onComplete = {
                _loading.postValue(false)
            },
            onError = {

            },
        ).collect {
            category.postValue(it)
            succesLoad.postValue("200")
        }
    }

    suspend fun addProduct(
        thumbnail: File,
        productimage1: File,
        productimage2: File,
        productimage3: File,
        productimage4: File,
        nama_produk: String,
        harga_produk: String,
        diskon_produk: String,
        kategori_produk: String,
        berat_produk: String,
        stock_default: String,
        deskripsi_produk: String,
        detail_info: String
    ) {
        // thumbnail
        val thumb = thumbnail.asRequestBody(
            getMimeType(thumbnail.path)!!.toMediaType()
        )
        val isthumbnail = MultipartBody.Part.createFormData(
            "thumbnail",
            thumbnail.name, thumb
        )
        //productimage1
        val image1 = productimage1.asRequestBody(
            getMimeType(productimage1.path)!!.toMediaType()
        )
        val isImage1 = MultipartBody.Part.createFormData(
            "productimage1",
            productimage1.name, image1
        )
        //productimage2
        val image2 = productimage2.asRequestBody(
            getMimeType(productimage2.path)!!.toMediaType()
        )
        val isImage2 = MultipartBody.Part.createFormData(
            "productimage2",
            productimage2.name, image2
        )
        //productimage3
        val image3 = productimage3.asRequestBody(
            getMimeType(productimage3.path)!!.toMediaType()
        )
        val isImage3 = MultipartBody.Part.createFormData(
            "productimage3",
            productimage3.name, image3
        )
        //productimage4
        val image4 = productimage4.asRequestBody(
            getMimeType(productimage4.path)!!.toMediaType()
        )
        val isImage4 = MultipartBody.Part.createFormData(
            "productimage4",
            productimage4.name, image4
        )
        val namaBody = nama_produk.toRequestBody("text/plain".toMediaType())
        val hargaBody = harga_produk.toRequestBody("text/plain".toMediaType())
        val diskonBody = diskon_produk.toRequestBody("text/plain".toMediaType())
        val kategoriBody = kategori_produk.toRequestBody("text/plain".toMediaType())
        val beratBody = berat_produk.toRequestBody("text/plain".toMediaType())
        val stokBody = stock_default.toRequestBody("text/plain".toMediaType())
        val descBody = deskripsi_produk.toRequestBody("text/plain".toMediaType())
        val detailBody = detail_info.toRequestBody("text/plain".toMediaType())
        productRepository.addProductAd(
            onStart = { _loading.postValue(true) },
            onComplete = { _loading.postValue(false) },
            onError = { },
            isthumbnail,
            isImage1,
            isImage2,
            isImage3,
            isImage4,
            namaBody,
            hargaBody,
            diskonBody,
            kategoriBody,
            beratBody,
            stokBody,
            descBody,
            detailBody
        ).collect {
            product.postValue(it)
            succesLoad.postValue("200")
        }
    }

    private fun getMimeType(path: String): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }
}