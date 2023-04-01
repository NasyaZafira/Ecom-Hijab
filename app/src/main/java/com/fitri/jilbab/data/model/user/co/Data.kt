package com.fitri.jilbab.data.model.user.co

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val courier: Courier,
    val orders: List<Order>,
    val shipping_address: ShippingAddress
): Parcelable