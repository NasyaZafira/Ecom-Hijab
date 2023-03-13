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

    suspend fun addProductAd(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit,
        thumbnail: MultipartBody.Part,
        productimage1: MultipartBody.Part,
        productimage2: MultipartBody.Part,
        productimage3: MultipartBody.Part,
        productimage4: MultipartBody.Part,
        nama_produk: RequestBody,
        harga_produk: RequestBody,
        diskon_produk: RequestBody,
        kategori_produk: RequestBody,
        berat_produk: RequestBody,
        stock_default: RequestBody,
        deskripsi_produk: RequestBody,
        detail_info: RequestBody
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
            detail_info
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