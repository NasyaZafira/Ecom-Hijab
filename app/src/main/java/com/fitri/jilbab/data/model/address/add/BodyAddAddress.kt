package com.fitri.jilbab.data.model.address.add

data class BodyAddAddress(
    val address: String,
    val city: String,
    val detail_address: String,
    val is_main_address: Boolean,
    val name: String,
    val phone: String,
    val province: String
)