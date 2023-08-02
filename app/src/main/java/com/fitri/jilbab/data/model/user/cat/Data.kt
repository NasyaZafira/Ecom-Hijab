package com.fitri.jilbab.data.model.user.cat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val archive: Boolean,
    val category: Category,
    val created_at: String,
    val discount: String,
    val id_category: String,
    val id_picture: Int,
    val id_product: Int,
    val is_active: String,
    val is_main: Int,
    val picture: String,
    val pictures: List<Picture>,
    val price: String,
    val product_description: String,
    val product_detail_info: String,
    val product_name: String,
    val slug_product: String,
    val rating: String,
    val stock: String,
    val updated_at: String,
    val weight_product: String
): Parcelable