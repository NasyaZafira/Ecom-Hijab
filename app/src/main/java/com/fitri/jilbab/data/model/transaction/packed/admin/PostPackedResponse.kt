package com.fitri.jilbab.data.model.transaction.packed.admin
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostPackedResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
): Parcelable