package com.fitri.jilbab.data.remote

import com.fitri.jilbab.data.model.address.ListAddressResponse
import com.fitri.jilbab.data.model.address.add.AddAddressResponse
import com.fitri.jilbab.data.model.address.add.BodyAddAddress
import com.fitri.jilbab.data.model.address.cities.CitiesResponse
import com.fitri.jilbab.data.model.address.edit.BodyEditAddress
import com.fitri.jilbab.data.model.address.edit.EditAddressResponse
import com.fitri.jilbab.data.model.address.province.ProvinceResponse
import com.fitri.jilbab.data.model.admin.category.CategoryListResponse
import com.fitri.jilbab.data.model.admin.category.add.CategoryAddResponse
import com.fitri.jilbab.data.model.admin.category.delete.DelCatResponse
import com.fitri.jilbab.data.model.admin.category.editCat.EditCatResponse
import com.fitri.jilbab.data.model.admin.product.delete.DelProductResponse
import com.fitri.jilbab.data.model.admin.product.list.ListPorductResponse
import com.fitri.jilbab.data.model.admin.product.newAdd.NewAddResponse
import com.fitri.jilbab.data.model.admin.product.newEdit.NewEditResponse
import com.fitri.jilbab.data.model.admin.usereg.UseregResponse
import com.fitri.jilbab.data.model.login.LoginBody
import com.fitri.jilbab.data.model.login.LoginResponse
import com.fitri.jilbab.data.model.profile.DetailProfileResponse
import com.fitri.jilbab.data.model.profile.edit.EditProfileBody
import com.fitri.jilbab.data.model.profile.edit.EditProfileResponse
import com.fitri.jilbab.data.model.profile.password.BodyPassword
import com.fitri.jilbab.data.model.profile.password.ChangePassResponse
import com.fitri.jilbab.data.model.profile.picture.ChangePictureResponse
import com.fitri.jilbab.data.model.register.SignUpBody
import com.fitri.jilbab.data.model.register.SignUpResponse
import com.fitri.jilbab.data.model.transaction.cancle.newCancle.NewCancleResponse
import com.fitri.jilbab.data.model.transaction.cancle.post.BodyCancleOrder
import com.fitri.jilbab.data.model.transaction.cancle.post.PostCancleResponse
import com.fitri.jilbab.data.model.transaction.complete.done.BodyDone
import com.fitri.jilbab.data.model.transaction.complete.done.DoneResponse
import com.fitri.jilbab.data.model.transaction.complete.response.CompleteResponse
import com.fitri.jilbab.data.model.transaction.incoming.admin.BodyStatusIncome
import com.fitri.jilbab.data.model.transaction.incoming.admin.PostIncomingResponse
import com.fitri.jilbab.data.model.transaction.incoming.newIncoming.NewIncomingResponse
import com.fitri.jilbab.data.model.transaction.packed.admin.BodyPackedPost
import com.fitri.jilbab.data.model.transaction.packed.admin.PostPackedResponse
import com.fitri.jilbab.data.model.transaction.packed.newPacked.NewPackedResponse
import com.fitri.jilbab.data.model.transaction.sent.newSent.NewSentResponse
import com.fitri.jilbab.data.model.transaction.unpaid.newUnpaid.NewUnpaidResponse
import com.fitri.jilbab.data.model.user.cart.add.AddCartResponse
import com.fitri.jilbab.data.model.user.cart.add.BodyCart
import com.fitri.jilbab.data.model.user.cart.list.CartResponse
import com.fitri.jilbab.data.model.user.cart.remove.RemoveResponse
import com.fitri.jilbab.data.model.user.cat.CatResponse
import com.fitri.jilbab.data.model.user.latestDt.DtLatestResponse
import com.fitri.jilbab.data.model.user.newCo.CoBody
import com.fitri.jilbab.data.model.user.newCo.NewCoResponse
import com.fitri.jilbab.data.model.user.newOrder.NewOrderResponse
import com.fitri.jilbab.data.model.user.order.BodyPlaceOrder
import com.fitri.jilbab.data.model.user.review.BodyReview
import com.fitri.jilbab.data.model.user.review.ReviewResponse
import com.fitri.jilbab.data.model.user.review.list.ListReviewResponse
import com.fitri.jilbab.data.model.user.search.SearchResponse
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiServices {


    @POST("auth/login")
    suspend fun login(
      @Body body: LoginBody
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
    ): ApiResponse<ListPorductResponse>

    @GET("product-category")
    suspend fun lisCategory(
    ): ApiResponse<CategoryListResponse>

    @Multipart
    @POST("product/add")
    suspend fun addProduct(
        @Part thumbnail: MultipartBody.Part?,
        @Part productimage1: MultipartBody.Part?,
        @Part productimage2: MultipartBody.Part?,
        @Part productimage3: MultipartBody.Part?,
        @Part productimage4: MultipartBody.Part?,
        @Part("nama_produk") nama_produk: RequestBody,
        @Part("harga_produk") harga_produk: RequestBody,
        @Part("diskon_produk") diskon_produk: RequestBody,
        @Part("kategori_produk") kategori_produk: RequestBody,
        @Part("berat_produk") berat_produk: RequestBody,
        @Part("stock_default") stock_default: RequestBody,
        @Part("deskripsi_produk") deskripsi_produk: RequestBody,
        @Part("detail_info") detail_info: RequestBody?,
        @Part("colors") colors: RequestBody?,
        @Part("stok_colors") stok_colors: RequestBody?
    ): ApiResponse<NewAddResponse>

    @Multipart
    @POST("product/update/{id_product}")
    suspend fun editProduct(
        @Path("id_product") id_product: Int,
        @Part thumbnail: MultipartBody.Part?,
        @Part productimage1: MultipartBody.Part?,
        @Part productimage2: MultipartBody.Part?,
        @Part productimage3: MultipartBody.Part?,
        @Part productimage4: MultipartBody.Part?,
        @Part("nama_produk") nama_produk: RequestBody,
        @Part("harga_produk") harga_produk: RequestBody,
        @Part("diskon_produk") diskon_produk: RequestBody,
        @Part("kategori_produk") kategori_produk: RequestBody,
        @Part("berat_produk") berat_produk: RequestBody,
        @Part("stock_default") stock_default: RequestBody,
        @Part("deskripsi_produk") deskripsi_produk: RequestBody,
        @Part("detail_info") detail_info: RequestBody?,
        @Part("colors") colors: RequestBody?,
        @Part("stok_colors") stok_colors: RequestBody?

    ): ApiResponse<NewEditResponse>

    @Multipart
    @POST("product-category/add")
    suspend fun addCategory(
        @Part("category_name") category_name: RequestBody,
        @Part category_image: MultipartBody.Part,
    ): ApiResponse<CategoryAddResponse>

    @GET("shipping-address")
    suspend fun listAddress(
    ): ApiResponse<ListAddressResponse>

    @GET("product/detail/{id_product}")
    suspend fun detailProduct(
        @Path("id_product") id_product: Int
    ): ApiResponse<DtLatestResponse>

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
        @Body body: CoBody
    ): ApiResponse<NewCoResponse>

    @POST("order/place-order")
    suspend fun placeOrder(
        @Body body: BodyPlaceOrder
    ): ApiResponse<NewOrderResponse>

    @GET("address/provinces")
    suspend fun listProv(
    ): ApiResponse<ProvinceResponse>

    @GET("address/cities/mobile/{id}")
    suspend fun listCity(
        @Path("id") id: Int
    ): ApiResponse<CitiesResponse>

    @POST("shipping-address/add")
    suspend fun addAddress(
        @Body body: BodyAddAddress
    ): ApiResponse<AddAddressResponse>
    @POST("shipping-address/update/{id_ship_address}")
    suspend fun editAddress(
        @Body body: BodyEditAddress,
        @Path("id_ship_address") id_ship_address: Int
    ): ApiResponse<EditAddressResponse>

    @GET("product/search")
    suspend fun searchProduct(
        @Query("q") q: String?
    ): ApiResponse<SearchResponse>

    @GET("product/by-category")
    suspend fun productCat(
        @Query("category") category: String?
    ): ApiResponse<CatResponse>

    @POST("profile/change-password")
    suspend fun changePass(
        @Body body: BodyPassword
    ): ApiResponse<ChangePassResponse>

    @POST("product/archive/{id_product}")
    suspend fun deletePeoduct(
        @Path("id_product") id_product: Int
    ): ApiResponse<DelProductResponse>

    @POST("review/add")
    suspend fun review(
        @Body body: BodyReview
    ): ApiResponse<ReviewResponse>

    @Multipart
    @POST("profile/change-picture")
    suspend fun changePicture(
        @Part profile_picture: MultipartBody.Part
    ): ApiResponse<ChangePictureResponse>

    @POST("product-category/archive/{id_category}")
    suspend fun delCategory(
        @Path("id_category") id_category: Int
    ): ApiResponse<DelCatResponse>

    @GET("review/list/{id_product}")
    suspend fun listReview(
        @Path("id_product") id_product: String
    ): ApiResponse<ListReviewResponse>

    @Multipart
    @POST("product-category/update/{id_category}")
    suspend fun editCat(
        @Path("id_category") id_category: Int,
        @Part("category_name") category_name: RequestBody,
        @Part category_image: MultipartBody.Part,
    ): ApiResponse<EditCatResponse>

    @GET("order/unpaid")
    suspend fun getUnpaid(
    ): ApiResponse<NewUnpaidResponse>

    @GET("order/incoming")
    suspend fun getIncoming(
    ): ApiResponse<NewIncomingResponse>
    @GET("order/packed")
    suspend fun getPacked(
    ): ApiResponse<NewPackedResponse>
    @GET("order/sent")
    suspend fun getSent(
    ): ApiResponse<NewSentResponse>
    @GET("order/canceled")
    suspend fun getCanceled(
    ): ApiResponse<NewCancleResponse>
    @GET("order/complete")
    suspend fun getComplete(
    ): ApiResponse<CompleteResponse>

    @POST("order/cancel-order")
    suspend fun postCancle(
        @Body body: BodyCancleOrder
    ): ApiResponse<PostCancleResponse>

    @POST("order/change-status/{id_order}")
    suspend fun postDone(
        @Path("id_order") id_order: String,
        @Body body: BodyDone
    ): ApiResponse<DoneResponse>

    @POST("order/change-status/{id_order}")
    suspend fun postPacked(
        @Path("id_order") id_order: String,
        @Body body: BodyStatusIncome
    ): ApiResponse<PostIncomingResponse>
    @POST("order/change-status/{id_order}")
    suspend fun postSent(
        @Path("id_order") id_order: String,
        @Body body: BodyPackedPost
    ): ApiResponse<PostPackedResponse>

    @GET("customer/list")
    suspend fun customer(
    ): ApiResponse<UseregResponse>
}