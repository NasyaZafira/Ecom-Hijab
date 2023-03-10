package com.fitri.jilbab.data.model.productAdm.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val category_name: String,
    val created_at: String,
    val id_category: Int,
    val slug_category: String,
    val updated_at: String
): Parcelable