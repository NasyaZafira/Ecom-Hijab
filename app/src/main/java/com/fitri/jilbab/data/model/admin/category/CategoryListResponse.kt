package com.fitri.jilbab.data.model.admin.category

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryListResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
): Parcelable