package com.fitri.jilbab.data.model.admin.product.add

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddProductResponse(
    @SerializedName("data")
    val `data`: Data,
    val message: String,
    val success: Boolean
):Parcelable