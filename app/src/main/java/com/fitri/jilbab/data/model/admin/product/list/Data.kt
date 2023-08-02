package com.fitri.jilbab.data.model.admin.product.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val archive: Int?,
    val category: Category?,
    val colors: String?,
    val created_at: String?,
    val discount: String?,
    val id_category: String?,
    val id_picture: Int?,
    val id_product: Int?,
    val is_active: String?,
    val is_main: Int?,
    val picture: String?,
    val pictures: List<Picture>?,
    val price: String,
    val product_description: String?,
    val product_detail_info: String?,
    val product_name: String?,
    val rating: String,
    val slug_product: String?,
    val stock: String?,
    val stok_colors: String?,
    val updated_at: String?,
    val weight_product: String?
): Parcelable