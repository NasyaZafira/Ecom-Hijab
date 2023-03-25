package com.fitri.jilbab.data.model.admin.category

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryListResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
): Parcelable