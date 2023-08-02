package com.fitri.jilbab.data.model.profile.password

data class BodyPassword(
    val confirmpassword: String,
    val currentpassword: String,
    val newpassword: String
)