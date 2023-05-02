package com.fitri.jilbab.data.model.transaction.complete

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompleteResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
): Parcelable