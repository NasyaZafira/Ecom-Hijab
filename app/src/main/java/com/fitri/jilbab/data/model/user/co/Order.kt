package com.fitri.jilbab.data.model.user.co

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val created_at: String,
    val id_cart: Int,
    val id_product: String,
    val id_user: String,
    val product: Product,
    val qty: Int,
    val updated_at: String
): Parcelable