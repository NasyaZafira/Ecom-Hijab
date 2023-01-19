package com.fitri.jilbab.data

data class LoginResponse(
    val access_token: String,
    val `data`: Data,
    val message: String,
    val success: Boolean
)