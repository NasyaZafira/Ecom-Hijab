package com.fitri.jilbab.repository

import com.fitri.jilbab.data.remote.ApiServices
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part
import javax.inject.Inject

class AdminRepository @Inject constructor(
    private val apiService: ApiServices,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun adminProduct(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ) = flow {
        val response = apiService.listProduct()
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            onError(this.message())
        }.onException {
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)

    suspend fun categoryAdmin(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
    ) = flow {
        val response = apiService.lisCategory()
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            onError(this.message())
        }.onException {
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)

    suspend fun deleteProduct(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        id_product: Int
    ) = flow {
        val response = apiService.deletePeoduct(id_product)
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            onError(this.message())
        }.onException {
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)

    suspend fun deleteCategory(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        id_category: Int
    ) = flow {
        val response = apiService.delCategory(id_category)
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            onError(this.message())
        }.onException {
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)

    suspend fun addCategory(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        category_name: RequestBody,
        category_image: MultipartBody.Part
    ) = flow {
        val response = apiService.addCategory(category_name, category_image)
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            onError(this.message())
        }.onException {
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)

    suspend fun editCategory(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        id_product: Int,
        category_name: RequestBody,
        category_image: MultipartBody.Part
    ) = flow {
        val response = apiService.editCat(id_product,category_name, category_image)
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            onError(this.message())
        }.onException {
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)

    suspend fun addProductAd(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        thumbnail: MultipartBody.Part? = null,
        productimage1: MultipartBody.Part? = null,
        productimage2: MultipartBody.Part? = null,
        productimage3: MultipartBody.Part? = null,
        productimage4: MultipartBody.Part? = null,
        nama_produk: RequestBody,
        harga_produk: RequestBody,
        diskon_produk: RequestBody,
        kategori_produk: RequestBody,
        berat_produk: RequestBody,
        stock_default: RequestBody,
        deskripsi_produk: RequestBody,
        detail_info: RequestBody?,
        colors: RequestBody?
    ) = flow {
        val response = apiService.addProduct(
            thumbnail,
            productimage1,
            productimage2,
            productimage3,
            productimage4,
            nama_produk,
            harga_produk,
            diskon_produk,
            kategori_produk,
            berat_produk,
            stock_default,
            deskripsi_produk,
            detail_info,
            colors
        )
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            onError(this.message())
        }.onException {
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)

    suspend fun editroductAd(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        id_product: Int,
        thumbnail: MultipartBody.Part? = null,
        productimage1: MultipartBody.Part? = null,
        productimage2: MultipartBody.Part? = null,
        productimage3: MultipartBody.Part? = null,
        productimage4: MultipartBody.Part? = null,
        nama_produk: RequestBody,
        harga_produk: RequestBody,
        diskon_produk: RequestBody,
        kategori_produk: RequestBody,
        berat_produk: RequestBody,
        stock_default: RequestBody,
        deskripsi_produk: RequestBody,
        detail_info: RequestBody?,
        colors: RequestBody?
    ) = flow {
        val response = apiService.editProduct(
            id_product,
            thumbnail,
            productimage1,
            productimage2,
            productimage3,
            productimage4,
            nama_produk,
            harga_produk,
            diskon_produk,
            kategori_produk,
            berat_produk,
            stock_default,
            deskripsi_produk,
            detail_info,
            colors
        )
        response.suspendOnSuccess {
            emit(data)
        }.onError {
            onError(this.message())
        }.onException {
            onError(this.message())
        }
    }
        .onStart { onStart() }
        .onCompletion { onComplete() }
        .flowOn(ioDispatcher)


}