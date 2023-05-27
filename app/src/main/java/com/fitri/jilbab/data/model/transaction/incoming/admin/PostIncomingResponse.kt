package com.fitri.jilbab.data.model.transaction.incoming.admin
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostIncomingResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
): Parcelable