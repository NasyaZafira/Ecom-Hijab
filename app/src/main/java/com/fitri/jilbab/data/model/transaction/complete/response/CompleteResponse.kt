package com.fitri.jilbab.data.model.transaction.complete.response
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompleteResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
): Parcelable