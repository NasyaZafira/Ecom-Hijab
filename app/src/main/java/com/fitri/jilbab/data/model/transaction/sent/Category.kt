package com.fitri.jilbab.data.model.transaction.sent
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val archive: Boolean,
    val category_image: String,
    val category_name: String,
    val created_at: String,
    val id_category: Int,
    val slug_category: String,
    val updated_at: String
): Parcelable