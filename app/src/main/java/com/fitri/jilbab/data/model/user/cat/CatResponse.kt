package com.fitri.jilbab.data.model.user.cat

import com.google.gson.annotations.SerializedName

data class CatResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
)