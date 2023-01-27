package com.fitri.jilbab.data.model

data class LoginResponse(
    val access_token: String,
    val `data`: Data,
    val message: String,
    val success: Boolean
)