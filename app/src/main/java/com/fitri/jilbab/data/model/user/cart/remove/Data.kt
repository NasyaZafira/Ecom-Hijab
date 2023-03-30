package com.fitri.jilbab.data.model.user.cart.remove

data class Data(
    val created_at: String,
    val id_cart: Int,
    val id_product: String,
    val id_user: String,
    val qty: Int,
    val updated_at: String
)