package com.fitri.jilbab.data.model.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class LoginBody(
    val email: String,
    val password: String
)