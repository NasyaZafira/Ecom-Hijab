package com.fitri.jilbab.data.model.register

data class SignUpBody(
    val email: String,
    val name: String,
    val password: String,
    val role: String
)