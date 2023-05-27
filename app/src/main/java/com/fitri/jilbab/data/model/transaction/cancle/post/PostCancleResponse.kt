package com.fitri.jilbab.data.model.transaction.cancle.post

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostCancleResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
): Parcelable