package com.fitri.jilbab.data.model.register

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignUpResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
): Parcelable