package com.fitri.jilbab.data.model.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(
    val access_token: String,
    val `data`: Data,
    val message: String,
    val success: Boolean
): Parcelable