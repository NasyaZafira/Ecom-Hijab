package com.fitri.jilbab.data.model.user.cart.list

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShippingAddres(
    val address: String,
    val created_at: String,
    val detail_address: String,
    val id_city: String,
    val id_province: String,
    val id_ship_address: Int,
    val id_user: String,
    val is_main_address: Int,
    val name: String,
    val phone: String,
    val updated_at: String
): Parcelable