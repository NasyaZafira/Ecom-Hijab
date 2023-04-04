package com.fitri.jilbab.data.remote

import com.fitri.jilbab.data.model.address.ListAddressResponse
import com.fitri.jilbab.data.model.admin.category.CategoryListResponse
import com.fitri.jilbab.data.model.admin.product.add.AddProductResponse
import com.fitri.jilbab.data.model.admin.product.list.ListProductResponse
import com.fitri.jilbab.data.model.login.LoginResponse
import com.fitri.jilbab.data.model.profile.DetailProfileResponse
import com.fitri.jilbab.data.model.profile.edit.EditProfileBody
import com.fitri.jilbab.data.model.profile.edit.EditProfileResponse
import com.fitri.jilbab.data.model.register.SignUpBody
import com.fitri.jilbab.data.model.register.SignUpResponse
import com.fitri.jilbab.data.model.user.DetailProductResponse
import com.fitri.jilbab.data.model.user.cart.add.AddCartResponse
import com.fitri.jilbab.data.model.user.cart.add.BodyCart
import com.fitri.jilbab.data.model.user.cart.list.CartResponse
import com.fitri.jilbab.data.model.user.cart.remove.RemoveResponse
import com.fitri.jilbab.data.model.user.checkout.BodyCheckout
import com.fitri.jilbab.data.model.user.checkout.CheckoutResponse
import com.fitri.jilbab.data.model.user.co.CoBody
import com.fitri.jilbab.data.model.user.co.CoResponse
import com.fitri.jilbab.data.model.user.order.BodyPlaceOrder
import com.fitri.jilbab.data.model.user.order.OrderResponse
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
        @Part("nama_produk") nama_produk : RequestBody,
        @Part("harga_produk") harga_produk : RequestBody,
        @Part("diskon_produk") diskon_produk : RequestBody,
        @Part("kategori_produk") kategori_produk : RequestBody,
        @Part("berat_produk") berat_produk : RequestBody,
        @Part("stock_default") stock_default : RequestBody,
        @Part("deskripsi_produk") deskripsi_produk : RequestBody,
        @Part("detail_info") detail_info : RequestBody?
        ): ApiResponse<AddProductResponse>

    @GET("shipping-address")
    suspend fun listAddress(
    ): ApiResponse<ListAddressResponse>

    @GET("product/detail/{id_product}")
    suspend fun detailProduct(
        @Path("id_product") id_product: Int
    ): ApiResponse<DetailProductResponse>

    @GET("cart/list")
    suspend fun cartProduct(
    ): ApiResponse<CartResponse>

    @POST("cart/remove-item/{id_cart}")
    suspend fun removeCart(
        @Path("id_cart") id_cart: Int
    ): ApiResponse<RemoveResponse>

    @POST("cart/add")
    suspend fun addCart(
        @Body body: BodyCart
    ): ApiResponse<AddCartResponse>

    @POST("cart/checkout")
    suspend fun checkout(
        @Body body : CoBody
    ): ApiResponse<CoResponse>

    @POST("order/place-order")
    suspend fun placeOrder(
        @Body body: BodyPlaceOrder
    ): ApiResponse<OrderResponse>
}