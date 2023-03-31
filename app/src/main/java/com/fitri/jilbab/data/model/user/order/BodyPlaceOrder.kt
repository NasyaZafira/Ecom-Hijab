package com.fitri.jilbab.data.model.user.order

data class BodyPlaceOrder(
    val courier: String,
    val courier_package: String,
    val delivery_estimate: String,
    val id_shipping_address: String,
    val shipping_cost: String,
    val total_price: String
)