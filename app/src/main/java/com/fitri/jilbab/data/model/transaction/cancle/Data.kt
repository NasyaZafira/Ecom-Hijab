package com.fitri.jilbab.data.model.transaction.cancle
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Data(
    val arrive_est_date: String,
    val complete_date: String,
    val courier: String,
    val courier_package: String,
    val created_at: String,
    val delivery_estimate: String,
    val id_order: String,
    val id_shipping_address: String,
    val id_user: String,
    val no_resi: String,
    val order_date: String,
    val payment: String,
    val products: List<Product>,
    val shipping_address: ShippingAddress,
    val shipping_cost: String,
    val snap_token: String,
    val status_order: String,
    val total_price: String,
    val updated_at: String,
    val user: User
): Parcelable