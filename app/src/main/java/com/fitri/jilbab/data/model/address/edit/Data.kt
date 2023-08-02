package com.fitri.jilbab.data.model.address.edit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val address: String,
    val created_at: String,
    val detail_address: String,
    val id_city: String,
    val id_province: String,
    val id_ship_address: Int,
    val id_user: Int,
    val is_main_address: Boolean,
    val name: String,
    val phone: String,
    val updated_at: String
): Parcelable