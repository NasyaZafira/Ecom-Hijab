package com.fitri.jilbab.data.model.user.cart.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val cart: List<Cart>,
    val shipping_address: List<ShippingAddres>
):Parcelable