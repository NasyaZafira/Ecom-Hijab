package com.fitri.jilbab.data.remote

import com.fitri.jilbab.data.model.login.LoginResponse
import com.fitri.jilbab.data.model.productAdm.list.ListProductResponse
import com.fitri.jilbab.data.model.profile.DetailProfileResponse
import com.fitri.jilbab.data.model.profile.edit.EditProfileBody
import com.fitri.jilbab.data.model.profile.edit.EditProfileResponse
import com.fitri.jilbab.data.model.register.SignUpBody
import com.fitri.jilbab.data.model.register.SignUpResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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

    @POST("product")
    suspend fun listProduct(
    ): ApiResponse<ListProductResponse>

}