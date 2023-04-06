package com.fitri.jilbab.data.model.user

import com.google.gson.annotations.SerializedName

data class DetailProductResponse(
    @SerializedName("data")
    val `data`: Data,
    val message: String,
    val success: Boolean
)