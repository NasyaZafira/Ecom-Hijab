package com.fitri.jilbab.data.model.admin.category.editCat

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditCatResponse(
    @SerializedName("data")
    val `data`: Data,
    val message: String,
    val success: Boolean
): Parcelable