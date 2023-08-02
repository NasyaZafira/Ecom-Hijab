package com.fitri.jilbab.data.model.admin.product.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListPorductResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
): Parcelable