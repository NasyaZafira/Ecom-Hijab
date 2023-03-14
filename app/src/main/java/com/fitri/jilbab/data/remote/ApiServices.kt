package com.fitri.jilbab.data.remote

import com.fitri.jilbab.data.model.address.ListAddressResponse
import com.fitri.jilbab.data.model.admin.category.CategoryListResponse
import com.fitri.jilbab.data.model.admin.product.add.AddProductResponse
import com.fitri.jilbab.data.model.login.LoginResponse
import com.fitri.jilbab.data.model.admin.product.list.ListProductResponse
import com.fitri.jilbab.data.model.profile.DetailProfileResponse
import com.fitri.jilbab.data.model.profile.edit.EditProfileBody
import com.fitri.jilbab.data.model.profile.edit.EditProfileResponse
import com.fitri.jilbab.data.model.register.SignUpBody
import com.fitri.jilbab.data.model.register.SignUpResponse
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiServices {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): ApiResponse<LoginResponse>

    @POST("auth/register")
    suspend fun postSignUp(
        @Body body: SignUpBody
    ): ApiResponse<SignUpResponse>

    @POST("profile/detail")
    suspend fun detailUser(
    ): ApiResponse<DetailProfileResponse>

    @POST("profile/update")
    suspend fun editProfile(
        @Body body: EditProfileBody
    ): ApiResponse<EditProfileResponse>

    @GET("product")
    suspend fun listProduct(
    ): ApiResponse<ListProductResponse>

    @GET("product-category")
    suspend fun lisCategory(
    ): ApiResponse<CategoryListResponse>

    @Multipart
    @POST("product/add")
    suspend fun addProduct(
        @Part thumbnail : MultipartBody.Part,
        @Part productimage1 : MultipartBody.Part?,
        @Part productimage2 : MultipartBody.Part?,
        @Part productimage3 : MultipartBody.Part?,
        @Part productimage4 : MultipartBody.Part?,
        @Part nama_produk : RequestBody,
        @Part harga_produk : RequestBody,
        @Part diskon_produk : RequestBody,
        @Part kategori_produk : RequestBody,
        @Part berat_produk : RequestBody,
        @Part stock_default : RequestBody,
        @Part deskripsi_produk : RequestBody,
        @Part detail_info : RequestBody?
        ): ApiResponse<AddProductResponse>

    @GET("shipping-address")
    suspend fun listAddress(
    ): ApiResponse<ListAddressResponse>
}