package com.fitri.jilbab.data.model.user.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResponse(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
): Parcelable