package com.fitri.jilbab.data.model.user.newCo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewCoResponse(
    @SerializedName("data")
    val `data`: Data,
    val message: String,
    val success: Boolean
): Parcelable